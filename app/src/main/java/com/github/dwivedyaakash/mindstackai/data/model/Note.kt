package com.github.dwivedyaakash.mindstackai.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val timeStamp: Long = System.currentTimeMillis()
)
