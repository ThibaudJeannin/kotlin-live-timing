import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.live.timing.ChronoLap
import io.live.timing.LapTime
import io.live.timing.TimeBoard
import org.slf4j.LoggerFactory
import kotlin.random.Random

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    val log = LoggerFactory.getLogger("Server")

    log.info("starting server on port $port")
    embeddedServer(Netty, port) {

        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                    )
                log.info("responded to /");
            }
//            static("/") {
//                log.info("handler request on static /")
//                resources("")
//            }

            get("/pilots") {
                log.info("handler request on /pilots")
                call.respond(pilots)
            }
            get("/laps") {
                log.info("handler request on /laps")
                val timeBoard = populateTimeBoard()
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