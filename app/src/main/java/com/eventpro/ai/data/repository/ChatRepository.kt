package com.eventpro.ai.data.repository

import com.eventpro.ai.data.model.ChatMessage
import com.eventpro.ai.data.model.ChatRoom
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
class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val chatRoomsCollection = firestore.collection("chatRooms")
    private val messagesCollection = firestore.collection("chatMessages")

    suspend fun createOrGetChatRoom(userId1: String, userId2: String, eventId: String): Resource<String> {
        return try {
            // Check if chat room exists
            val participantIds = listOf(userId1, userId2).sorted()
            val existingRoom = chatRoomsCollection
                .whereArrayContains("participants", userId1)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(ChatRoom::class.java) }
                .firstOrNull { it.participants.sorted() == participantIds && it.eventId == eventId }

            if (existingRoom != null) {
                Resource.Success(existingRoom.id)
            } else {
                // Create new chat room
                val docRef = chatRoomsCollection.document()
                val chatRoom = ChatRoom(
                    id = docRef.id,
                    participants = listOf(userId1, userId2),
                    eventId = eventId
                )
                docRef.set(chatRoom).await()
                Resource.Success(docRef.id)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create chat room")
        }
    }

    suspend fun sendMessage(chatRoomId: String, message: ChatMessage): Resource<Unit> {
        return try {
            val docRef = messagesCollection.document()
            val messageWithId = message.copy(id = docRef.id)
            docRef.set(messageWithId).await()
            
            // Update chat room last message
            chatRoomsCollection.document(chatRoomId)
                .update(
                    mapOf(
                        "lastMessage" to message.message,
                        "lastMessageTime" to message.timestamp
                    )
                ).await()
            
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to send message")
        }
    }

    fun observeMessages(chatRoomId: String): Flow<Resource<List<ChatMessage>>> = callbackFlow {
        val listener = messagesCollection
            .whereEqualTo("receiverId", chatRoomId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load messages"))
                    return@addSnapshotListener
                }
                
                val messages = snapshot?.documents?.mapNotNull {
                    it.toObject(ChatMessage::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(messages))
            }
        
        awaitClose { listener.remove() }
    }

    fun observeUserChatRooms(userId: String): Flow<Resource<List<ChatRoom>>> = callbackFlow {
        val listener = chatRoomsCollection
            .whereArrayContains("participants", userId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to load chat rooms"))
                    return@addSnapshotListener
                }
                
                val chatRooms = snapshot?.documents?.mapNotNull {
                    it.toObject(ChatRoom::class.java)
                } ?: emptyList()
                
                trySend(Resource.Success(chatRooms))
            }
        
        awaitClose { listener.remove() }
    }
}
