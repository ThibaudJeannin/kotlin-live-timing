import io.live.timing.TimeBoard
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.*

private val scope = MainScope()

val app = fc<PropsWithChildren> {
    var timeBoard by useState(TimeBoard(emptyList()))

    useEffectOnce {
        scope.launch {
            val pilots = getPilots()
            val laps = getLaps()
            timeBoard = TimeBoard(pilots)
            timeBoard.insertLapTimes(*laps.toTypedArray())
        }
    }

    h1 {
        +"Kotlin LiveTiming"
    }
    div {
        h3 {
            +"Tableau des temps"
        }
    }

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
                            +("${lapTime.minutes}" +
                                    ":${lapTime.seconds.toString().padStart(2, '0')}" +
                                    ".${lapTime.millis.toString().padStart(3, '0')}")
                        } else {
                            +"no time"
                        }
                    }
                }
            }
        }

    }
}