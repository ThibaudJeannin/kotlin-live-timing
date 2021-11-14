import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.live.timing.ChronoLap
import io.live.timing.Pilot
import io.live.timing.TimeBoard

val endpoint = "http://localhost:9090"

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getTimeBoard() : TimeBoard {
    return jsonClient.get("$endpoint/timeboard")
}

suspend fun getPilots() : List<Pilot> {
    return jsonClient.get("$endpoint/pilots")
}

suspend fun getLaps() : List<ChronoLap> {
    return jsonClient.get("$endpoint/laps")
}