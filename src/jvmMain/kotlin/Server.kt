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
import java.io.File
import kotlin.random.Random

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    routing {
        get("/") {
            log.debug("request is ${call.request}")
            call.respondText(
                this::class.java.classLoader.getResource("index.html")!!.readText(),
                ContentType.Text.Html,
                HttpStatusCode.OK
            )
        }
        get("/app") {
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "index.html")
                    .toString()
            )
            call.respondText(
                this::class.java.classLoader.getResource("index.html")!!.readText().substring(0, 150),
                ContentType.Text.Html,
                HttpStatusCode.OK
            )
        }
        static("/") {
            resource("/", "index.html")
            resource("/index", "index.html")
            resources("")
        }

        get("/pilots") {
            call.respond(pilots)
        }
        get("/laps") {
            val timeBoard = populateTimeBoard()
            call.respond(timeBoard.allLaps)
        }
        get("/test") {
            call.respondText("<p>foo</p><br><p>bar</p>", ContentType.Text.Html)
        }
        get("/test-large") {
            call.respondText("<p>foo</p><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><p>bar</p>", ContentType.Text.Html)
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