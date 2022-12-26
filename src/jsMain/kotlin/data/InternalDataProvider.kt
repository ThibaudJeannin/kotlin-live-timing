package data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*

import io.live.timing.ChronoLap
import io.live.timing.Pilot
import io.live.timing.Race
import io.live.timing.TimeBoard
import kotlinx.browser.window
import kotlinx.serialization.json.Json

class InternalDataProvider : LiveTimingDataProvider {
    private val baseUrl = "${window.location.protocol}//${window.location.host}"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            register(ContentType.Application.Json, KotlinxSerializationConverter(Json))
        }
    }

    override suspend fun getPilots(): List<Pilot> {
        return httpClient.get("$baseUrl/api/pilots").body()
    }

    override suspend fun getLaps(): List<ChronoLap> {
        return httpClient.get("$baseUrl/api/laps").body()
    }

    override suspend fun getTimeBoard(raceId: String): TimeBoard {
        val timeBoard = TimeBoard(this.getPilots())
        for (lap in this.getLaps()) {
            timeBoard.insertLapTime(lap)
        }
        return timeBoard
    }

    override suspend fun getRaces(): List<Race> {
        return listOf(
            Race("Kyalami 2026", "kya2026"),
            Race("Sepang 2026", "sep2028")
        )
    }
}
