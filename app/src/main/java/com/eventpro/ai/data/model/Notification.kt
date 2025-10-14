package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val message: String = "",
    val type: NotificationType = NotificationType.GENERAL,
    val eventId: String = "",
    val sessionId: String = "",
    val isRead: Boolean = false,
    val actionUrl: String = "",
    val imageUrl: String = "",
    val timestamp: Timestamp = Timestamp.now()
) : Parcelable

enum class NotificationType {
    GENERAL,
    SESSION_REMINDER,
    SCHEDULE_CHANGE,
    NETWORKING_MATCH,
    MESSAGE,
    ANNOUNCEMENT,
    PAYMENT,
    AI_RECOMMENDATION
}
