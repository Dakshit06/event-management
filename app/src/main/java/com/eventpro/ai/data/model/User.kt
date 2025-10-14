package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val role: UserRole = UserRole.ATTENDEE,
    val interests: List<String> = emptyList(),
    val professionalBackground: String = "",
    val company: String = "",
    val jobTitle: String = "",
    val bio: String = "",
    val linkedInUrl: String = "",
    val location: String = "",
    val networkingGoals: String = "",
    val fcmToken: String = "",
    val createdAt: @RawValue Timestamp = Timestamp.now(),
    val updatedAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

enum class UserRole {
    ATTENDEE,
    ORGANIZER,
    SPONSOR,
    SPEAKER,
    ADMIN
}
