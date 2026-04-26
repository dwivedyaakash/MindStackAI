package com.github.dwivedyaakash.mindstackai.repository

import com.github.dwivedyaakash.mindstackai.data.local.NoteDao
import com.github.dwivedyaakash.mindstackai.data.model.Note
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override suspend fun getAllNotesForRAG(): List<Note> = noteDao.getAllNotesForRAG()

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}
