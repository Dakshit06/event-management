package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Session(
    val id: String = "",
    val eventId: String = "",
    val title: String = "",
    val description: String = "",
    val speakerIds: List<String> = emptyList(),
    val type: SessionType = SessionType.TALK,
    val track: String = "",
    val room: String = "",
    val startTime: Timestamp = Timestamp.now(),
    val endTime: Timestamp = Timestamp.now(),
    val duration: Int = 60, // in minutes
    val capacity: Int = 0,
    val attendeeIds: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val level: SessionLevel = SessionLevel.INTERMEDIATE,
    val language: String = "English",
    val isRecorded: Boolean = false,
    val recordingUrl: String = "",
    val slidesUrl: String = "",
    val resources: List<String> = emptyList(),
    val aiSummary: String = "",
    val keyTakeaways: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now()
) : Parcelable

enum class SessionType {
    KEYNOTE,
    TALK,
    WORKSHOP,
    PANEL,
    FIRESIDE_CHAT,
    NETWORKING,
    BREAK,
    OTHER
}

enum class SessionLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    ALL_LEVELS
}
