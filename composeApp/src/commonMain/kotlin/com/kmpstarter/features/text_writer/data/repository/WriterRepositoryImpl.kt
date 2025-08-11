package com.kmpstarter.features.text_writer.data.repository

import com.kmpstarter.core.AppConstants
import com.kmpstarter.features.text_writer.data.data_source.dtos.GeminiResponse
import com.kmpstarter.features.text_writer.data.data_source.room.mappers.toEntity
import com.kmpstarter.features.text_writer.data.data_source.room.services.RoomServices
import com.kmpstarter.features.text_writer.domain.models.WriterItem
import com.kmpstarter.features.text_writer.domain.repository.WriterRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class WriterRepositoryImpl(
    private val httpClient: HttpClient,
    private val roomServices: RoomServices
) : WriterRepository {


    companion object {
        const val GEMINI_API_KEY = AppConstants.GEMINI_API_KEY
        const val MODEL_ID = "gemini-2.5-flash"
        const val GENERATE_CONTENT_API = "generateContent"
    }


    override suspend fun generateText(prompt: String): WriterItem {
        val jsonPayload = """
            {
                "contents": [
                  {
                    "role": "user",
                    "parts": [
                      {
                        "text": "$prompt"
                      },
                    ]
                  },
                ],
                "generationConfig": {
                  "thinkingConfig": {
                    "thinkingBudget": -1,
                  },
                  "responseMimeType": "application/json",
                  "responseSchema": {
                      "type": "object",
                      "properties": {
                        "title": {
                          "type": "string"
                        },
                        "content": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "title",
                        "content"
                      ],
                      "propertyOrdering": [
                        "title",
                        "content"
                      ]
                    },
                },
            }
        """.trimIndent()
        val url = "https://generativelanguage.googleapis.com/v1beta/models/${MODEL_ID}:${GENERATE_CONTENT_API}?key=${GEMINI_API_KEY}"

        val response = httpClient.post(
            url = Url(
                urlString = url
            )
        ) {
            contentType(ContentType.Application.Json)
            setBody(jsonPayload)
        }


        val geminiResponse = response.body<GeminiResponse>()

        val rawjsonText = geminiResponse.candidates[0].content.parts[0].text

        val writerItem: WriterItem = Json.decodeFromString(
            string = rawjsonText
        )

        return writerItem
    }

    override suspend fun getHistory() = roomServices.getWriterItems().map { writerEntitiesList ->
        writerEntitiesList.map { writerEntity ->
            writerEntity.toWriterItem()
        }
    }

    override suspend fun insertHistory(writerItem: WriterItem): Result<WriterItem> {
        return try {
           roomServices.insertWriterItem(writerEntity = writerItem.toEntity())
            Result.success(writerItem)

        } catch (e : Exception){
            Result.failure(e)
        }

    }
}