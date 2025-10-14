package com.eventpro.ai.data.repository

import com.eventpro.ai.data.model.AIRecommendation
import com.eventpro.ai.data.model.NetworkingMatch
import com.eventpro.ai.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val recommendationsCollection = firestore.collection("aiRecommendations")
    private val matchesCollection = firestore.collection("networkingMatches")

    suspend fun generateRecommendations(userId: String, eventId: String): Resource<List<AIRecommendation>> {
        return try {
            // TODO: Implement actual AI recommendation logic
            // This is a placeholder that fetches existing recommendations
            val snapshot = recommendationsCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("eventId", eventId)
                .get()
                .await()
            
            val recommendations = snapshot.documents.mapNotNull {
                it.toObject(AIRecommendation::class.java)
            }
            
            Resource.Success(recommendations)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to generate recommendations")
        }
    }

    fun observeRecommendations(userId: String, eventId: String): Flow<Resource<List<AIRecommendation>>> = callbackFlow {
        val listener = recommendationsCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("eventId", eventId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load recommendations"))
                    return@addSnapshotListener
                }
                
                val recommendations = snapshot?.documents?.mapNotNull {
                    it.toObject(AIRecommendation::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(recommendations))
            }
        
        awaitClose { listener.remove() }
    }

    suspend fun generateNetworkingMatches(userId: String, eventId: String): Resource<List<NetworkingMatch>> {
        return try {
            // TODO: Implement actual AI matchmaking logic using NLP
            // This is a placeholder that fetches existing matches
            val snapshot = matchesCollection
                .whereEqualTo("eventId", eventId)
                .get()
                .await()
            
            val matches = snapshot.documents.mapNotNull {
                it.toObject(NetworkingMatch::class.java)
            }.filter {
                it.userId1 == userId || it.userId2 == userId
            }
            
            Resource.Success(matches)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to generate matches")
        }
    }

    suspend fun saveRecommendation(recommendation: AIRecommendation): Resource<Unit> {
        return try {
            val docRef = recommendationsCollection.document()
            val recWithId = recommendation.copy(id = docRef.id)
            docRef.set(recWithId).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to save recommendation")
        }
    }
}
