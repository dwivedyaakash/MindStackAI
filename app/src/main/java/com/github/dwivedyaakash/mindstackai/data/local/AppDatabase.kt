package com.github.dwivedyaakash.mindstackai.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.github.dwivedyaakash.mindstackai.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}
