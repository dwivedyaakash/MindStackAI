package com.github.dwivedyaakash.mindstackai.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class GeminiEmbeddingClient(private val apiKey: String) {

    suspend fun getEmbedding(text: String): List<Float> = withContext(Dispatchers.IO) {
        val url =
            URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-embedding-2:embedContent?key=$apiKey")
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val textPart = JSONObject().put("text", text)
            val partsArray = JSONArray().put(textPart)
            val contentObj = JSONObject().put("parts", partsArray)
            val payloadObj = JSONObject()
                .put("model", "models/gemini-embedding-2")
                .put("content", contentObj)

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(payloadObj.toString())
                writer.flush()
            }

            if (connection.responseCode in 200..299) {
                val responseStr = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(responseStr)
                val valuesArray = jsonObject.getJSONObject("embedding").getJSONArray("values")
                val vector = mutableListOf<Float>()
                for (i in 0 until valuesArray.length()) {
                    vector.add(valuesArray.getDouble(i).toFloat())
                }
                return@withContext vector
            } else {
                val errorStr = connection.errorStream?.bufferedReader()?.use { it.readText() }
                    ?: "Unknown Error"
                throw Exception("API Error ${connection.responseCode}: $errorStr")
            }
        } finally {
            connection.disconnect()
        }
    }
}