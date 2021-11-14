import csstype.System
import io.live.timing.ChronoLap
import io.live.timing.Pilot
import io.live.timing.TimeBoard
import kotlinext.js.jsObject
import kotlinx.css.alignSelf
import kotlinx.css.display
import kotlinx.css.marginLeft
import kotlinx.css.position
import react.PropsWithChildren
import react.RBuilder
import react.RComponent
import react.*
import react.dom.*
import styled.css
import styled.styledDiv
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

external interface AppState : State {
    var running: String
}

@ExperimentalTime
class App : RComponent<PropsWithChildren, AppState>() {

    override fun AppState.init() {
    }

    override fun RBuilder.render() {
        h1 {
            +"Kotlin LiveTiming"
        }
        div {
            h3 {
                +"Tableau des temps"
            }
        }

        val timeBoard = populateTimeBoard()
        table {
            thead {
                tr {
                    th {
                        +"number"
                    }
                    th {
                        +"pilot"
                    }
                    th {
                        +"laptime"
                    }
                }
            }
            tbody {
                for ((pilot, lapTime) in timeBoard.sortedResults()) {

                    tr {
                        td {
                            +"${pilot.number}"
                        }
                        td {
                            +pilot.name
                        }
                        td {
                            if (lapTime != null) {
                                lapTime.toComponents { minutes, seconds, nanoseconds ->
                                    +("${minutes.toLong()}" +
                                            ":${seconds.toLong().toString().padStart(2, '0')}" +
                                            ".${(nanoseconds.toLong() / 1000000).toString().padStart(3, '0')}")
                                }
                            } else {
                                +"no time"
                            }
                        }
                    }
                }
            }

        }
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
                    Duration.milliseconds((Random.nextInt() % 4000) + 70000),
                    Random.nextBoolean()
                )
            )
        }
        timeBoard.updateTimeBoard()
        return timeBoard
    }
}