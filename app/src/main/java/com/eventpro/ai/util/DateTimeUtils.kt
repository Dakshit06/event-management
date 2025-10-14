package com.eventpro.ai.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    
    private const val DATE_FORMAT = "MMM dd, yyyy"
    private const val TIME_FORMAT = "h:mm a"
    private const val DATE_TIME_FORMAT = "MMM dd, yyyy 'at' h:mm a"
    
    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }
    
    fun formatTime(date: Date): String {
        val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }
    
    fun formatDateTime(date: Date): String {
        val formatter = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }
    
    fun getRelativeTime(date: Date): String {
        val now = Date()
        val diff = now.time - date.time
        
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
            else -> formatDate(date)
        }
    }
    
    fun isToday(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_YEAR)
        
        calendar.time = date
        val dateDay = calendar.get(Calendar.DAY_OF_YEAR)
        
        return today == dateDay
    }
    
    fun isTomorrow(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = calendar.get(Calendar.DAY_OF_YEAR)
        
        calendar.time = date
        val dateDay = calendar.get(Calendar.DAY_OF_YEAR)
        
        return tomorrow == dateDay
    }
}
