import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.live.timing.ChronoLap
import io.live.timing.LapTime
import io.live.timing.TimeBoard
import kotlin.random.Random

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        static("/") {
            resource("/", "index.html")
            resource("style.css")
            resource("kotlin-live-timing.js")
        }

        route("/api") {
            get("/pilots") {
                call.respond(pilots)
            }
            get("/laps") {
                val timeBoard = populateTimeBoard()
                call.respond(timeBoard.allLaps)
            }
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
}

private fun populateTimeBoard(): TimeBoard {
    val timeBoard = TimeBoard(pilots)
    for (i in 1..42) {
        timeBoard.insertLapTime(
            ChronoLap(
                pilots[Random.nextInt(0, pilots.size)],
                LapTime(1, Random.nextInt(21, 27), Random.nextInt(0, 1000)),
                Random.nextBoolean()
            )
        )
    }
    timeBoard.updateTimeBoard()
    return timeBoard
}