package com.github.dwivedyaakash.mindstackai.data.local

import androidx.room3.TypeConverter

class VectorConverter {
    @TypeConverter
    fun fromVector(vector: List<Float>?): String? {
        // Convert the list of floats to a comma-separated string
        return vector?.joinToString(",")
    }

    @TypeConverter
    fun toVector(data: String?): List<Float>? {
        // Convert the string back to a list of floats
        return data?.split(",")?.mapNotNull { it.toFloatOrNull() }
    }
}