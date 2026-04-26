package com.github.dwivedyaakash.mindstackai.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.github.dwivedyaakash.mindstackai.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // For UI
    @Query("SELECT * FROM notes ORDER BY timeStamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    // For RAG context
    @Query("SELECT * FROM notes ORDER BY timeStamp DESC")
    suspend fun getAllNotesForRAG(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
