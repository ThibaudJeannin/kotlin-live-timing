package com.ergast

import com.ergast.serialization.ConstructorsResponse
import com.ergast.serialization.QualifyingResponse
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*

//Data from http://ergast.com/mrd/
class ErgastAPIClient {

    private val httpClient = HttpClient {
        install(ResponseObserver) {
            onResponse { response ->
                println("HTTP status: ${response.status.value}")
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getQualifyingResult(): QualifyingResponse {
        return httpClient.get("http://ergast.com/api/f1/2021/22/qualifying.json")
    }

    suspend fun getConstructors(): ConstructorsResponse {
        return httpClient.get("http://ergast.com/api/f1/2021/constructors.json")
    }
}