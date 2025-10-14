package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val organizerId: String = "",
    val organizerName: String = "",
    val category: EventCategory = EventCategory.CONFERENCE,
    val imageUrl: String = "",
    val bannerUrl: String = "",
    val venue: Venue = Venue(),
    val startDate: @RawValue Timestamp = Timestamp.now(),
    val endDate: @RawValue Timestamp = Timestamp.now(),
    val registrationDeadline: @RawValue Timestamp = Timestamp.now(),
    val capacity: Int = 0,
    val registeredCount: Int = 0,
    val tags: List<String> = emptyList(),
    val ticketTypes: List<TicketType> = emptyList(),
    val speakerIds: List<String> = emptyList(),
    val sponsorIds: List<String> = emptyList(),
    val agenda: List<String> = emptyList(), // List of session IDs
    val status: EventStatus = EventStatus.DRAFT,
    val isPublished: Boolean = false,
    val hashtag: String = "",
    val websiteUrl: String = "",
    val socialMediaLinks: Map<String, String> = emptyMap(),
    val createdAt: @RawValue Timestamp = Timestamp.now(),
    val updatedAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

@Parcelize
data class Venue(
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val zipCode: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val floorPlanUrl: String = "",
    val rooms: List<Room> = emptyList()
) : Parcelable

@Parcelize
data class Room(
    val id: String = "",
    val name: String = "",
    val capacity: Int = 0,
    val floor: Int = 1,
    val amenities: List<String> = emptyList()
) : Parcelable

enum class EventCategory {
    CONFERENCE,
    WORKSHOP,
    SEMINAR,
    NETWORKING,
    EXHIBITION,
    WEBINAR,
    MEETUP,
    OTHER
}

enum class EventStatus {
    DRAFT,
    PUBLISHED,
    ONGOING,
    COMPLETED,
    CANCELLED
}
