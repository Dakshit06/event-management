package com.eventpro.ai

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EventProApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_ID_GENERAL,
                    "General Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "General event notifications"
                },
                NotificationChannel(
                    CHANNEL_ID_REMINDERS,
                    "Session Reminders",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Reminders for upcoming sessions"
                },
                NotificationChannel(
                    CHANNEL_ID_MESSAGES,
                    "Messages",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "New messages and networking updates"
                },
                NotificationChannel(
                    CHANNEL_ID_AI,
                    "AI Recommendations",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Personalized AI recommendations"
                }
            )
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }
    
    companion object {
        const val CHANNEL_ID_GENERAL = "general_notifications"
        const val CHANNEL_ID_REMINDERS = "session_reminders"
        const val CHANNEL_ID_MESSAGES = "messages"
        const val CHANNEL_ID_AI = "ai_recommendations"
    }
}
