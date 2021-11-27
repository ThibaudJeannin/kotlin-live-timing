import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.live.timing.ChronoLap
import io.live.timing.Pilot
import io.live.timing.TimeBoard
import kotlinx.browser.window

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}
val baseUrl = "${window.location.protocol}//${window.location.host}"

suspend fun getPilots(): List<Pilot> {
    return jsonClient.get("$baseUrl/pilots")
}

suspend fun getLaps(): List<ChronoLap> {
    return jsonClient.get("$baseUrl/laps")
}

suspend fun getTimeBoard(): TimeBoard {
    return jsonClient.get("$baseUrl/timeboard")
}

