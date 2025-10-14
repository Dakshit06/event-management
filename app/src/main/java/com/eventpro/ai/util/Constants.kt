package com.eventpro.ai.util

object Constants {
    
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_EVENTS = "events"
    const val COLLECTION_SESSIONS = "sessions"
    const val COLLECTION_TICKETS = "tickets"
    const val COLLECTION_SPEAKERS = "speakers"
    const val COLLECTION_SPONSORS = "sponsors"
    const val COLLECTION_CHAT_ROOMS = "chatRooms"
    const val COLLECTION_CHAT_MESSAGES = "chatMessages"
    const val COLLECTION_NOTIFICATIONS = "notifications"
    const val COLLECTION_AI_RECOMMENDATIONS = "aiRecommendations"
    const val COLLECTION_NETWORKING_MATCHES = "networkingMatches"
    
    // Shared Preferences Keys
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_FCM_TOKEN = "fcm_token"
    const val PREF_THEME_MODE = "theme_mode"
    
    // Intent Extras
    const val EXTRA_EVENT_ID = "event_id"
    const val EXTRA_SESSION_ID = "session_id"
    const val EXTRA_TICKET_ID = "ticket_id"
    const val EXTRA_USER_ID = "user_id"
    const val EXTRA_NOTIFICATION = "notification"
    
    // Request Codes
    const val REQUEST_CODE_GOOGLE_SIGN_IN = 1001
    const val REQUEST_CODE_QR_SCAN = 1002
    const val REQUEST_CODE_IMAGE_PICK = 1003
    
    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 40
    
    // Cache Expiry (in milliseconds)
    const val CACHE_EXPIRY_TIME = 5 * 60 * 1000L // 5 minutes
    
    // AI Configuration
    const val MAX_RECOMMENDATIONS = 10
    const val MAX_NETWORKING_MATCHES = 20
    const val MIN_MATCH_SCORE = 0.6f
    
    // Image Upload
    const val MAX_IMAGE_SIZE_MB = 5
    const val IMAGE_QUALITY = 85
    
    // Notification Channels
    const val CHANNEL_GENERAL = "general_notifications"
    const val CHANNEL_REMINDERS = "session_reminders"
    const val CHANNEL_MESSAGES = "messages"
    const val CHANNEL_AI = "ai_recommendations"
    
    // Deep Link Prefixes
    const val DEEP_LINK_PREFIX = "eventpro://"
    const val DEEP_LINK_EVENT = "${DEEP_LINK_PREFIX}event/"
    const val DEEP_LINK_SESSION = "${DEEP_LINK_PREFIX}session/"
    const val DEEP_LINK_PROFILE = "${DEEP_LINK_PREFIX}profile/"
    
    // Error Messages
    const val ERROR_NETWORK = "Network error. Please check your connection."
    const val ERROR_GENERIC = "Something went wrong. Please try again."
    const val ERROR_AUTH = "Authentication error. Please sign in again."
    const val ERROR_PERMISSION = "Permission denied. Please grant necessary permissions."
    
    // Success Messages
    const val SUCCESS_REGISTER = "Registration successful!"
    const val SUCCESS_LOGIN = "Welcome back!"
    const val SUCCESS_EVENT_CREATED = "Event created successfully!"
    const val SUCCESS_TICKET_PURCHASED = "Ticket purchased successfully!"
    
    // API Timeouts
    const val NETWORK_TIMEOUT = 30L // seconds
    const val READ_TIMEOUT = 30L // seconds
    const val WRITE_TIMEOUT = 30L // seconds
}
