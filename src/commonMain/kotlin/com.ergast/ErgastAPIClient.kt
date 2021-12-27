package com.ergast

import com.ergast.serialization.ConstructorsResponse
import com.ergast.serialization.DriversResponse
import com.ergast.serialization.QualifyingResponse
import io.ktor.client.*
import io.ktor.client.features.cache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*

class ErgastAPIClient {

    //Data from http://ergast.com/mrd/
    private val baseUrl = "http://ergast.com"
    private val season2021endpoint = "$baseUrl/api/f1/2021"

    private val httpClient = HttpClient {
        install(ResponseObserver) {
            onResponse { response ->
                if (response.status.value >= 300) {
                    println("HTTP status: ${response.status.value}")
                }
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
        install(HttpCache)
    }

    suspend fun getQualifyingResult(): QualifyingResponse {
        return httpClient.get("$season2021endpoint/22/qualifying.json")
    }

    suspend fun getConstructors(): ConstructorsResponse {
        return httpClient.get("$season2021endpoint/constructors.json")
    }

    suspend fun getConstructorsForDriver(driver : String): ConstructorsResponse {
        return httpClient.get("$season2021endpoint/drivers/$driver/constructors.json")
    }

    suspend fun getDrivers(): DriversResponse {
        return httpClient.get("$season2021endpoint/drivers.json")
    }
}