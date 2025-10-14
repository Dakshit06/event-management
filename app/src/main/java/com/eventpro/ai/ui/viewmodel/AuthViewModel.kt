package com.eventpro.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventpro.ai.data.model.User
import com.eventpro.ai.data.repository.AuthRepository
import com.eventpro.ai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    init {
        checkAuthStatus()
        observeAuthChanges()
    }
    
    private fun checkAuthStatus() {
        viewModelScope.launch {
            val currentUser = authRepository.currentUser
            if (currentUser != null) {
                val user = authRepository.getUserFromFirestore(currentUser.uid)
                _authState.value = AuthState(isLoggedIn = true, user = user)
            }
        }
    }
    
    private fun observeAuthChanges() {
        viewModelScope.launch {
            authRepository.observeAuthState().collect { firebaseUser ->
                if (firebaseUser != null) {
                    val user = authRepository.getUserFromFirestore(firebaseUser.uid)
                    _authState.value = AuthState(isLoggedIn = true, user = user)
                } else {
                    _authState.value = AuthState(isLoggedIn = false, user = null)
                }
            }
        }
    }
    
    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            when (val result = authRepository.signInWithEmail(email, password)) {
                is Resource.Success -> {
                    _authState.value = AuthState(
                        isLoading = false,
                        isLoggedIn = true,
                        user = result.data
                    )
                }
                is Resource.Error -> {
                    _authState.value = AuthState(
                        isLoading = false,
                        isLoggedIn = false,
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun signUpWithEmail(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            when (val result = authRepository.signUpWithEmail(email, password, displayName)) {
                is Resource.Success -> {
                    _authState.value = AuthState(
                        isLoading = false,
                        isLoggedIn = true,
                        user = result.data
                    )
                }
                is Resource.Error -> {
                    _authState.value = AuthState(
                        isLoading = false,
                        isLoggedIn = false,
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            when (val result = authRepository.signInWithGoogle(idToken)) {
                is Resource.Success -> {
                    _authState.value = AuthState(
                        isLoading = false,
                        isLoggedIn = true,
                        user = result.data
                    )
                }
                is Resource.Error -> {
                    _authState.value = AuthState(
                        isLoading = false,
                        isLoggedIn = false,
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _authState.value = AuthState(isLoggedIn = false, user = null)
        }
    }
    
    fun updateProfile(user: User) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            
            when (authRepository.updateUserProfile(user)) {
                is Resource.Success -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        user = user
                    )
                }
                is Resource.Error -> {
                    _authState.value = _authState.value.copy(isLoading = false)
                }
                else -> {}
            }
        }
    }
    
    fun resetPassword(email: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            when (val result = authRepository.resetPassword(email)) {
                is Resource.Success -> {
                    onResult(true, "Password reset email sent")
                }
                is Resource.Error -> {
                    onResult(false, result.message ?: "Failed to send reset email")
                }
                else -> {}
            }
        }
    }
    
    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}
