package com.eventpro.ai.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class AIRecommendation(
    val id: String = "",
    val userId: String = "",
    val eventId: String = "",
    val type: RecommendationType = RecommendationType.SESSION,
    val itemId: String = "",
    val title: String = "",
    val description: String = "",
    val score: Float = 0f,
    val reasons: List<String> = emptyList(),
    val imageUrl: String = "",
    val viewed: Boolean = false,
    val interacted: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
) : Parcelable

enum class RecommendationType {
    SESSION,
    SPEAKER,
    NETWORKING,
    SPONSOR,
    CONTENT
}

@Parcelize
data class SentimentAnalysis(
    val id: String = "",
    val eventId: String = "",
    val source: String = "", // "chat", "social", "feedback"
    val sentiment: Sentiment = Sentiment.NEUTRAL,
    val score: Float = 0f,
    val topics: List<String> = emptyList(),
    val keywords: List<String> = emptyList(),
    val timestamp: Timestamp = Timestamp.now(),
    val aggregatedData: Map<String, Any> = emptyMap()
) : Parcelable

enum class Sentiment {
    POSITIVE,
    NEUTRAL,
    NEGATIVE,
    MIXED
}

@Parcelize
data class PredictiveAnalytics(
    val id: String = "",
    val eventId: String = "",
    val predictedAttendance: Int = 0,
    val confidenceScore: Float = 0f,
    val sessionPopularity: Map<String, Float> = emptyMap(),
    val peakTimes: List<String> = emptyList(),
    val resourceNeeds: ResourcePrediction = ResourcePrediction(),
    val generatedAt: Timestamp = Timestamp.now()
) : Parcelable

@Parcelize
data class ResourcePrediction(
    val foodCount: Int = 0,
    val beverageCount: Int = 0,
    val staffNeeded: Int = 0,
    val parkingSpaces: Int = 0
) : Parcelable

@Parcelize
data class CrowdHeatmap(
    val id: String = "",
    val eventId: String = "",
    val locations: Map<String, Int> = emptyMap(), // location -> crowd count
    val timestamp: Timestamp = Timestamp.now(),
    val hotspots: List<String> = emptyList()
) : Parcelable
