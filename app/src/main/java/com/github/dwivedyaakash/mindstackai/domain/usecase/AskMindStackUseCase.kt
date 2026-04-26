package com.github.dwivedyaakash.mindstackai.domain.usecase

import com.github.dwivedyaakash.mindstackai.repository.NoteRepository
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

class AskMindStackUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val generativeModel: GenerativeModel
) {
    suspend operator fun invoke(userQuestion: String): String {
        val notes = repository.getAllNotesForRAG()
        val contextTextForRAG = notes.joinToString(separator = "\n- ") { it.content }

        val prompt = """
            You are MindStackAI, an intelligent memory assistant. 
            Answer the user's question using ONLY the provided context notes below. 
            If the answer cannot be deduced from the context, explicitly state: "I don't have that information in your MindStack."
            
            Context Notes:
            - $contextTextForRAG
            
            User Question: $userQuestion
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "MindStack could not generate a response."
        } catch (e: Exception) {
            "Error connecting to AI: ${e.localizedMessage}"
        }
    }
}
