package com.eventpro.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventpro.ai.data.model.Event
import com.eventpro.ai.data.repository.EventRepository
import com.eventpro.ai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventListState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    
    private val _eventListState = MutableStateFlow(EventListState())
    val eventListState: StateFlow<EventListState> = _eventListState.asStateFlow()
    
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent.asStateFlow()
    
    init {
        loadEvents()
    }
    
    private fun loadEvents() {
        viewModelScope.launch {
            _eventListState.value = _eventListState.value.copy(isLoading = true)
            
            eventRepository.observeEvents().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _eventListState.value = EventListState(
                            isLoading = false,
                            events = resource.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _eventListState.value = EventListState(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _eventListState.value = _eventListState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
    
    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            when (val result = eventRepository.getEvent(eventId)) {
                is Resource.Success -> {
                    _selectedEvent.value = result.data
                }
                is Resource.Error -> {
                    // Handle error
                }
                else -> {}
            }
        }
    }
    
    fun searchEvents(query: String) {
        viewModelScope.launch {
            _eventListState.value = _eventListState.value.copy(isLoading = true)
            
            when (val result = eventRepository.searchEvents(query)) {
                is Resource.Success -> {
                    _eventListState.value = EventListState(
                        isLoading = false,
                        events = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _eventListState.value = EventListState(
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun registerForEvent(eventId: String, userId: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            when (val result = eventRepository.registerForEvent(eventId, userId)) {
                is Resource.Success -> {
                    onResult(true, "Successfully registered for event")
                }
                is Resource.Error -> {
                    onResult(false, result.message ?: "Failed to register")
                }
                else -> {}
            }
        }
    }
}
