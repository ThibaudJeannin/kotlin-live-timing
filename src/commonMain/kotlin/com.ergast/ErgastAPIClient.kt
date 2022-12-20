package com.ergast

import com.ergast.serialization.responses.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json

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
        install(ContentNegotiation) {
            register(ContentType.Application.Json, KotlinxSerializationConverter(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }))
        }

        install(HttpCache)
    }

    suspend fun getQualifyingResult(round: Int): QualifyingResponse {
        return httpClient.get("$season2021endpoint/$round/qualifying.json").body()
    }

    suspend fun getConstructors(): ConstructorsResponse {
        return httpClient.get("$season2021endpoint/constructors.json").body()
    }

    suspend fun getConstructorsForDriver(driver: String): ConstructorsResponse {
        return httpClient.get("$season2021endpoint/drivers/$driver/constructors.json").body()
    }

    suspend fun getDrivers(): DriversResponse {
        return httpClient.get("$season2021endpoint/drivers.json").body()
    }

    suspend fun getRaceResults(): RaceResultResponse {
        return httpClient.get("$season2021endpoint/22/results.json").body()
    }

    suspend fun getRacesList(): RaceScheduleResponse {
        return httpClient.get("$season2021endpoint.json").body()
    }
}