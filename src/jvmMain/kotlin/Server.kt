import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.live.timing.ChronoLap
import io.live.timing.LapTime
import io.live.timing.Pilot
import io.live.timing.TimeBoard
import kotlin.random.Random

fun main() {
    embeddedServer(Netty, 9090) {
        val timeBoard = populateTimeBoard()

        routing {
            get("/pilots") {
                call.respond(timeBoard.pilots)
            }
            get("/laps") {
                call.respond(timeBoard.allLaps)
            }

            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                method(HttpMethod.Get)
                method(HttpMethod.Post)
                method(HttpMethod.Delete)
                anyHost()
            }
            install(Compression) {
                gzip()
            }
        }
    }.start(wait = true)
}

private fun populateTimeBoard(): TimeBoard {
    val pilots = listOf(
        Pilot("Max Verstappen", 33),
        Pilot("Valtteri Bottas", 77),
        Pilot("Charles Leclerc", 16),
        Pilot("Lando Norris", 4),
        Pilot("Pierre Gasly", 10),
        Pilot("Fernando Alonso", 14),
        Pilot("Kimi Raikkonen", 7),
        Pilot("Mick Schumacher", 47)
    )

    val timeBoard = TimeBoard(pilots)

    for (i in 1..42) {
        timeBoard.insertLapTime(
            ChronoLap(
                pilots[i % pilots.size],
                LapTime(1, Random.nextInt() % 4, Random.nextInt() % 1000),
                Random.nextBoolean()
            )
        )
    }
    timeBoard.updateTimeBoard()
    return timeBoard
}