package com.github.dwivedyaakash.mindstackai.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.TypeConverters
import com.github.dwivedyaakash.mindstackai.data.local.VectorConverter

@Entity(tableName = "notes")
@TypeConverters(VectorConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val vector: List<Float>,
    val timeStamp: Long = System.currentTimeMillis()
)
