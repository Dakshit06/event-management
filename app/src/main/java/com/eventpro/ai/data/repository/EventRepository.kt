package com.eventpro.ai.data.repository

import com.eventpro.ai.data.model.Event
import com.eventpro.ai.data.model.EventStatus
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
class EventRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val eventsCollection = firestore.collection("events")

    suspend fun createEvent(event: Event): Resource<String> {
        return try {
            val docRef = eventsCollection.document()
            val eventWithId = event.copy(id = docRef.id)
            docRef.set(eventWithId).await()
            Resource.Success(docRef.id)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create event")
        }
    }

    suspend fun updateEvent(event: Event): Resource<Unit> {
        return try {
            eventsCollection.document(event.id).set(event).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update event")
        }
    }

    suspend fun deleteEvent(eventId: String): Resource<Unit> {
        return try {
            eventsCollection.document(eventId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to delete event")
        }
    }

    suspend fun getEvent(eventId: String): Resource<Event> {
        return try {
            val document = eventsCollection.document(eventId).get().await()
            val event = document.toObject(Event::class.java)
            if (event != null) {
                Resource.Success(event)
            } else {
                Resource.Error("Event not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to get event")
        }
    }

    fun observeEvents(): Flow<Resource<List<Event>>> = callbackFlow {
        val listener = eventsCollection
            .whereEqualTo("isPublished", true)
            .whereEqualTo("status", EventStatus.PUBLISHED.name)
            .orderBy("startDate", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load events"))
                    return@addSnapshotListener
                }
                
                val events = snapshot?.documents?.mapNotNull {
                    it.toObject(Event::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(events))
            }
        
        awaitClose { listener.remove() }
    }

    fun observeEventsByOrganizer(organizerId: String): Flow<Resource<List<Event>>> = callbackFlow {
        val listener = eventsCollection
            .whereEqualTo("organizerId", organizerId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load events"))
                    return@addSnapshotListener
                }
                
                val events = snapshot?.documents?.mapNotNull {
                    it.toObject(Event::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(events))
            }
        
        awaitClose { listener.remove() }
    }

    suspend fun searchEvents(query: String): Resource<List<Event>> {
        return try {
            val snapshot = eventsCollection
                .whereEqualTo("isPublished", true)
                .get()
                .await()
            
            val events = snapshot.documents.mapNotNull {
                it.toObject(Event::class.java)
            }.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.tags.any { tag -> tag.contains(query, ignoreCase = true) }
            }
            
            Resource.Success(events)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to search events")
        }
    }

    suspend fun registerForEvent(eventId: String, userId: String): Resource<Unit> {
        return try {
            val eventRef = eventsCollection.document(eventId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(eventRef)
                val event = snapshot.toObject(Event::class.java)
                    ?: throw Exception("Event not found")
                
                if (event.registeredCount >= event.capacity) {
                    throw Exception("Event is full")
                }
                
                transaction.update(eventRef, "registeredCount", event.registeredCount + 1)
            }.await()
            
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to register for event")
        }
    }
}
