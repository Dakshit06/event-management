package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Speaker(
    val id: String = "",
    val name: String = "",
    val bio: String = "",
    val photoUrl: String = "",
    val company: String = "",
    val jobTitle: String = "",
    val email: String = "",
    val linkedInUrl: String = "",
    val twitterUrl: String = "",
    val websiteUrl: String = "",
    val expertise: List<String> = emptyList(),
    val sessionIds: List<String> = emptyList(),
    val eventIds: List<String> = emptyList(),
    val createdAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

@Parcelize
data class Sponsor(
    val id: String = "",
    val name: String = "",
    val logoUrl: String = "",
    val description: String = "",
    val tier: SponsorTier = SponsorTier.BRONZE,
    val websiteUrl: String = "",
    val boothNumber: String = "",
    val boothLocation: String = "",
    val representatives: List<String> = emptyList(),
    val eventIds: List<String> = emptyList(),
    val packageFeatures: List<String> = emptyList(),
    val leadCount: Int = 0,
    val createdAt: @RawValue Timestamp = Timestamp.now()
) : Parcelable

enum class SponsorTier {
    PLATINUM,
    GOLD,
    SILVER,
    BRONZE,
    PARTNER
}
