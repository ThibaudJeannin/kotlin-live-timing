import csstype.Border
import csstype.FontFamily
import io.live.timing.TimeBoard
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.border
import react.PropsWithChildren
import react.dom.*
import react.fc
import react.useEffectOnce
import react.useState
import styled.*

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

    styledDiv {
        css {
            borderStyle = BorderStyle.solid
            borderColor = Color.red
            borderWidth = 5.px
            position = Position.relative
            top = 10.px
            left = 10.px
        }
        styledTable {
            css {
                borderCollapse = BorderCollapse.collapse
            }
            styledThead {
                tr {
                    styledTh {
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
            styledTbody {
                for ((pilot, lapTime) in timeBoard.sortedResults()) {

                    styledTr {
                        css {
                            backgroundColor = Color.slateGrey
                            borderRadius = 10.px
                            color = Color.whiteSmoke
                            height = 40.px
                        }
                        styledTd {
                            styledDiv {
                                css {
                                    val size = 25.px

                                    textAlign = TextAlign.center
                                    backgroundColor = Color.darkSlateGrey
                                    borderRadius = 10.px
                                    width = size
                                    height = size
                                    lineHeight = LineHeight("$size")
                                    fontWeight = FontWeight("900")
                                    color = Color.beige
                                }
                                +"${pilot.number}"
                            }
                        }
                        styledTd {
                            css {
                                fontWeight = FontWeight("700")
                            }
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
}