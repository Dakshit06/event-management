package com.eventpro.ai.data.repository

import com.eventpro.ai.data.model.Session
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
class SessionRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val sessionsCollection = firestore.collection("sessions")

    suspend fun createSession(session: Session): Resource<String> {
        return try {
            val docRef = sessionsCollection.document()
            val sessionWithId = session.copy(id = docRef.id)
            docRef.set(sessionWithId).await()
            Resource.Success(docRef.id)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create session")
        }
    }

    suspend fun updateSession(session: Session): Resource<Unit> {
        return try {
            sessionsCollection.document(session.id).set(session).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update session")
        }
    }

    fun observeSessionsByEvent(eventId: String): Flow<Resource<List<Session>>> = callbackFlow {
        val listener = sessionsCollection
            .whereEqualTo("eventId", eventId)
            .orderBy("startTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load sessions"))
                    return@addSnapshotListener
                }
                
                val sessions = snapshot?.documents?.mapNotNull {
                    it.toObject(Session::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(sessions))
            }
        
        awaitClose { listener.remove() }
    }

    suspend fun addUserToSession(sessionId: String, userId: String): Resource<Unit> {
        return try {
            val sessionRef = sessionsCollection.document(sessionId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(sessionRef)
                val session = snapshot.toObject(Session::class.java)
                    ?: throw Exception("Session not found")
                
                if (session.attendeeIds.contains(userId)) {
                    throw Exception("Already registered for this session")
                }
                
                if (session.capacity > 0 && session.attendeeIds.size >= session.capacity) {
                    throw Exception("Session is full")
                }
                
                val updatedAttendees = session.attendeeIds + userId
                transaction.update(sessionRef, "attendeeIds", updatedAttendees)
            }.await()
            
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to register for session")
        }
    }

    suspend fun removeUserFromSession(sessionId: String, userId: String): Resource<Unit> {
        return try {
            val sessionRef = sessionsCollection.document(sessionId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(sessionRef)
                val session = snapshot.toObject(Session::class.java)
                    ?: throw Exception("Session not found")
                
                val updatedAttendees = session.attendeeIds.filter { it != userId }
                transaction.update(sessionRef, "attendeeIds", updatedAttendees)
            }.await()
            
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to unregister from session")
        }
    }
}
