package com.github.dwivedyaakash.mindstackai.utils

import kotlin.math.sqrt

// Calculates how similar two vectors are. Returns a value between -1.0 and 1.0 (closer to 1.0 is highly relevant).
fun cosineSimilarity(v1: List<Float>, v2: List<Float>): Float {
    if (v1.size != v2.size) return 0f

    var dotProduct = 0.0f
    var normA = 0.0f
    var normB = 0.0f

    for (i in v1.indices) {
        dotProduct += v1[i] * v2[i]
        normA += v1[i] * v1[i]
        normB += v2[i] * v2[i]
    }

    return if (normA == 0.0f || normB == 0.0f) 0.0f else (dotProduct / (sqrt(normA) * sqrt(normB)))
}