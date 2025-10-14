package com.eventpro.ai.data.repository

import com.eventpro.ai.data.model.Ticket
import com.eventpro.ai.data.model.TicketStatus
import com.eventpro.ai.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val ticketsCollection = firestore.collection("tickets")

    suspend fun createTicket(ticket: Ticket): Resource<String> {
        return try {
            val docRef = ticketsCollection.document()
            val ticketWithId = ticket.copy(id = docRef.id)
            docRef.set(ticketWithId).await()
            Resource.Success(docRef.id)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create ticket")
        }
    }

    fun observeUserTickets(userId: String): Flow<Resource<List<Ticket>>> = callbackFlow {
        val listener = ticketsCollection
            .whereEqualTo("userId", userId)
            .orderBy("purchaseDate", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load tickets"))
                    return@addSnapshotListener
                }
                
                val tickets = snapshot?.documents?.mapNotNull {
                    it.toObject(Ticket::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(tickets))
            }
        
        awaitClose { listener.remove() }
    }

    suspend fun checkInTicket(ticketId: String): Resource<Unit> {
        return try {
            ticketsCollection.document(ticketId)
                .update(
                    mapOf(
                        "status" to TicketStatus.CHECKED_IN.name,
                        "checkInTime" to com.google.firebase.Timestamp.now()
                    )
                ).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to check in")
        }
    }
}
