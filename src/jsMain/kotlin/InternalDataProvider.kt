import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.live.timing.ChronoLap
import io.live.timing.Pilot
import kotlinx.browser.window

class InternalDataProvider : LiveTimingDataProvider {
    private val baseUrl = "${window.location.protocol}//${window.location.host}"

    private val jsonClient = HttpClient {
        install(JsonFeature) { serializer = KotlinxSerializer() }
    }

    override suspend fun getPilots(): List<Pilot> {
        return jsonClient.get("$baseUrl/api/pilots")
    }

    override suspend fun getLaps(): List<ChronoLap> {
        return jsonClient.get("$baseUrl/api/laps")
    }


}
