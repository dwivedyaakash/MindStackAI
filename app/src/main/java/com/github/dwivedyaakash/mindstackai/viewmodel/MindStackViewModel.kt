package com.github.dwivedyaakash.mindstackai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dwivedyaakash.mindstackai.data.model.ChatMessage
import com.github.dwivedyaakash.mindstackai.data.model.Note
import com.github.dwivedyaakash.mindstackai.domain.usecase.AskMindStackUseCase
import com.github.dwivedyaakash.mindstackai.domain.usecase.SaveNoteUseCase
import com.github.dwivedyaakash.mindstackai.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MindStackViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val askMindStackUseCase: AskMindStackUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatHistory: StateFlow<List<ChatMessage>> = _chatHistory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun saveNote(content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            try {
                saveNoteUseCase(content.trim())
            } catch (e: Exception) {
                println("Error saving note with embedding: ${e.localizedMessage}")
            }
        }
    }

    fun askQuestion(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            val userMessage = ChatMessage(isUser = true, text = query)
            _chatHistory.value += userMessage

            _isLoading.value = true

            val aiResponseText = askMindStackUseCase(query)

            val aiMessage = ChatMessage(isUser = false, text = aiResponseText)
            _chatHistory.value += aiMessage

            _isLoading.value = false
        }
    }
}