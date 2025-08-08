//package com.kmpstarter
//
//import com.kmpstarter.core.AppConstants
//import com.kmpstarter.core.utils.logging.Log
//import io.ktor.client.HttpClient
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//import io.ktor.client.statement.bodyAsText
//import io.ktor.http.ContentType
//import io.ktor.http.Url
//import io.ktor.http.contentType
//
//class GeminiTest(
//    private val httpClient : HttpClient
//) {
//
//    companion object{
//    const val GEMINI_API_KEY= AppConstants.GEMINI_API_KEY
//    const val MODEL_ID="gemini-2.5-flash"
//    const val GENERATE_CONTENT_API="generateContent"
//        }
//
//   suspend fun generateText(prompt : String){
//        val jsonPayload = """
//            {
//                "contents": [
//                  {
//                    "role": "user",
//                    "parts": [
//                      {
//                        "text": "$prompt"
//                      },
//                    ]
//                  },
//                ],
//                "generationConfig": {
//                  "thinkingConfig": {
//                    "thinkingBudget": -1,
//                  },
//                  "responseMimeType": "application/json",
//                  "responseSchema": {
//                      "type": "object",
//                      "properties": {
//                        "title": {
//                          "type": "string"
//                        },
//                        "content": {
//                          "type": "string"
//                        }
//                      },
//                      "required": [
//                        "title",
//                        "content"
//                      ],
//                      "propertyOrdering": [
//                        "title",
//                        "content"
//                      ]
//                    },
//                },
//            }
//        """.trimIndent()
//        val url = "https://generativelanguage.googleapis.com/v1beta/models/${MODEL_ID}:${GENERATE_CONTENT_API}?key=${GEMINI_API_KEY}"
//
//        val response = httpClient.post(
//            url = Url(
//                urlString = url
//            )
//        ){
//            contentType(ContentType.Application.Json)
//            setBody(jsonPayload)
//        }
//
//       val responseText = response.bodyAsText()
//
//       Log.d(
//           tag = "GEMINI_RESPONSE",
//           responseText
//       )
//    }
//}