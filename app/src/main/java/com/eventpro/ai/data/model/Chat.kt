package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val senderPhotoUrl: String = "",
    val receiverId: String = "",
    val message: String = "",
    val type: MessageType = MessageType.TEXT,
    val mediaUrl: String = "",
    val timestamp: @RawValue Timestamp = Timestamp.now(),
    val isRead: Boolean = false,
    val eventId: String = ""
) : Parcelable

enum class MessageType {
    TEXT,
    IMAGE,
    FILE,
    MEETING_REQUEST,
    SYSTEM
}

@Parcelize
data class ChatRoom(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastMessageTime: @RawValue Timestamp = Timestamp.now(),
    val unreadCount: Map<String, Int> = emptyMap(),
    val eventId: String = "",
    val createdAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

@Parcelize
data class NetworkingMatch(
    val id: String = "",
    val userId1: String = "",
    val userId2: String = "",
    val matchScore: Float = 0f,
    val matchReasons: List<String> = emptyList(),
    val status: MatchStatus = MatchStatus.SUGGESTED,
    val eventId: String = "",
    val createdAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

enum class MatchStatus {
    SUGGESTED,
    CONNECTED,
    DECLINED,
    MEETING_SCHEDULED
}
