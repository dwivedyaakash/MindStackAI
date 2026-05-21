package com.github.dwivedyaakash.mindstackai.domain.usecase

import com.github.dwivedyaakash.mindstackai.data.model.Note
import com.github.dwivedyaakash.mindstackai.data.remote.GeminiEmbeddingClient
import com.github.dwivedyaakash.mindstackai.repository.NoteRepository
import jakarta.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val embeddingClient: GeminiEmbeddingClient
) {
    suspend operator fun invoke(content: String) {
        val vector = embeddingClient.getEmbedding(content)
        val note = Note(content = content, vector = vector)
        repository.insertNote(note)
    }
}