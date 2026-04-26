package com.github.dwivedyaakash.mindstackai.repository

import com.github.dwivedyaakash.mindstackai.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getAllNotesForRAG(): List<Note>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}
