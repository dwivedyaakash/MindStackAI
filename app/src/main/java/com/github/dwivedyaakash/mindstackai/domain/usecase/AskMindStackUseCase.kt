package com.github.dwivedyaakash.mindstackai.domain.usecase

import com.github.dwivedyaakash.mindstackai.data.remote.GeminiEmbeddingClient
import com.github.dwivedyaakash.mindstackai.repository.NoteRepository
import com.github.dwivedyaakash.mindstackai.utils.cosineSimilarity
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

class AskMindStackUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val textGenerativeModel: GenerativeModel,
    private val embeddingClient: GeminiEmbeddingClient
) {
    suspend operator fun invoke(userQuestion: String): String {
        val queryVector = try {
            embeddingClient.getEmbedding(userQuestion)
        } catch (e: Exception) {
            return "Error analyzing query: ${e.localizedMessage}"
        }

        val allNotes = repository.getAllNotesForRAG()

        val scoredNotes = allNotes.map { note ->
            val score = cosineSimilarity(queryVector, note.vector)
            Pair(note, score)
        }

        val topRelevantNotes = scoredNotes
            .sortedByDescending { it.second }
            .take(3)
            .map { it.first.content }

        val contextText = topRelevantNotes.joinToString(separator = "\n- ")

        val prompt = """
            You are MindStackAI. Answer the user's question using ONLY the provided context below. 
            
            Context:
            - $contextText
            
            User Question: $userQuestion
        """.trimIndent()

        return try {
            val response = textGenerativeModel.generateContent(prompt)
            response.text ?: "Could not generate a response."
        } catch (e: Exception) {
            "Error connecting to AI: ${e.localizedMessage}"
        }
    }
}
