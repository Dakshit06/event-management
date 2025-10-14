package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Ticket(
    val id: String = "",
    val eventId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val ticketType: TicketType = TicketType(),
    val qrCode: String = "",
    val purchaseDate: @RawValue Timestamp = Timestamp.now(),
    val status: TicketStatus = TicketStatus.ACTIVE,
    val checkInTime: Timestamp? = null,
    val sessionIds: List<String> = emptyList(), // Sessions registered for
    val transactionId: String = "",
    val createdAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

@Parcelize
data class TicketType(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val currency: String = "USD",
    val quantity: Int = 0,
    val available: Int = 0,
    val perks: List<String> = emptyList(),
    val validFrom: @RawValue Timestamp = Timestamp.now(),
    val validUntil: @RawValue Timestamp = Timestamp.now()
) : Parcelable

enum class TicketStatus {
    ACTIVE,
    CHECKED_IN,
    CANCELLED,
    REFUNDED,
    EXPIRED
}
