package com.eventpro.ai.util

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isStrongPassword(password: String): Boolean {
        if (password.length < 8) return false
        
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecial
    }
    
    fun getPasswordStrength(password: String): PasswordStrength {
        return when {
            password.length < 6 -> PasswordStrength.WEAK
            password.length < 8 -> PasswordStrength.MEDIUM
            isStrongPassword(password) -> PasswordStrength.STRONG
            else -> PasswordStrength.MEDIUM
        }
    }
    
    fun isValidPhoneNumber(phone: String): Boolean {
        if (phone.isBlank()) return false
        return phone.length >= 10 && phone.all { it.isDigit() || it == '+' || it == '-' || it == ' ' }
    }
    
    fun isValidUrl(url: String): Boolean {
        if (url.isBlank()) return false
        return android.util.Patterns.WEB_URL.matcher(url).matches()
    }
}

enum class PasswordStrength {
    WEAK,
    MEDIUM,
    STRONG
}
