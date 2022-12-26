import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.live.timing.ChronoLap
import io.live.timing.LapTime
import io.live.timing.TimeBoard
import kotlinx.serialization.json.Json
import kotlin.random.Random

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        static("/") {
            resource("/", "index.html")
            resource("style.css")
            resource("kotlin-live-timing.js")
            resource("favicon.ico")
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
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, KotlinxSerializationConverter(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }))
    }

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }

    install(Compression) {
        gzip()
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