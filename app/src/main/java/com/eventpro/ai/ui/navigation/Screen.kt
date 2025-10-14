package com.eventpro.ai.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object EventList : Screen("event_list")
    object EventDetail : Screen("event_detail/{eventId}") {
        fun createRoute(eventId: String) = "event_detail/$eventId"
    }
    object Schedule : Screen("schedule/{eventId}") {
        fun createRoute(eventId: String) = "schedule/$eventId"
    }
    object SessionDetail : Screen("session_detail/{sessionId}") {
        fun createRoute(sessionId: String) = "session_detail/$sessionId"
    }
    object Tickets : Screen("tickets")
    object TicketDetail : Screen("ticket_detail/{ticketId}") {
        fun createRoute(ticketId: String) = "ticket_detail/$ticketId"
    }
    object Networking : Screen("networking/{eventId}") {
        fun createRoute(eventId: String) = "networking/$eventId"
    }
    object Chat : Screen("chat/{chatRoomId}") {
        fun createRoute(chatRoomId: String) = "chat/$chatRoomId"
    }
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object AIRecommendations : Screen("ai_recommendations/{eventId}") {
        fun createRoute(eventId: String) = "ai_recommendations/$eventId"
    }
    object SpeakerDetail : Screen("speaker_detail/{speakerId}") {
        fun createRoute(speakerId: String) = "speaker_detail/$speakerId"
    }
    object VenueMap : Screen("venue_map/{eventId}") {
        fun createRoute(eventId: String) = "venue_map/$eventId"
    }
}
