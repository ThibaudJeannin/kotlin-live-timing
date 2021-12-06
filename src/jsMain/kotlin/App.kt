import io.live.timing.LapTime
import io.live.timing.Pilot
import io.live.timing.TimeBoard
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.TD
import react.PropsWithChildren
import react.dom.div
import react.dom.h3
import react.dom.tr
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
            val newTimeBoard = TimeBoard(pilots)
            newTimeBoard.insertLapTimes(*laps.toTypedArray())
            newTimeBoard.updateTimeBoard()
            timeBoard = newTimeBoard
        }
    }

    styledDiv {
        css {
            backgroundColor = Color("#FF1E00")
            color = Color.white
            position = Position.relative
            height = 120.px
            width = 120.px
            borderRadius = 12.px
            borderStyle = BorderStyle.solid
            lineHeight = LineHeight(30.px.toString())
            fontWeight = FontWeight("900")
            fontSize = 16.pt
            paddingLeft = 10.px
            display = Display.flex
            alignItems = Align.center
            boxSizing = BoxSizing.borderBox
        }
        +"Kotlin\nLiveTiming"
    }
    div {
        h3 {
            +"Tableau des temps"
        }
    }

    styledDiv {
        css {
            marginLeft = LinearDimension.auto
            marginRight = LinearDimension.auto
            width = LinearDimension.auto
        }
        styledTable {
            css {
                borderCollapse = BorderCollapse.collapse
                width = 100.pct
            }
            styledThead {
                css {
                    color = Color.darkGrey
                }
                tr {
                    styledTh {
                        +"position"
                        css {
                            width = 100.px
                        }
                    }
                    styledTh {
                        +"number"
                        css {
                            width = 100.px
                        }
                    }
                    styledTh {
                        +"pilot"
                        css {
                            width = LinearDimension.fillAvailable
                        }
                    }
                    styledTh {
                        +"laptime"
                        css {
                            width = 25.pct
                        }
                    }
                }
            }
            styledTbody {
                var i = 0
                for ((pilot: Pilot, lapTime: LapTime?) in timeBoard.sortedResults()) {
                    i++
                    styledTr {
                        css {
                            textAlign = TextAlign.center

                            height = 40.px
                            padding(5.px)

                            backgroundColor = when {
                                i % 2 == 0 -> Color.whiteSmoke
                                else -> Color.white
                            }
                            borderStyle = BorderStyle.dashed
                            borderLeftStyle = BorderStyle.none
                            borderRightStyle = BorderStyle.none
                            borderRadius = 10.px
                            borderWidth = 2.px
                        }
                        styledTd {
                            numberInBox(i)
                        }
                        styledTd {
                            numberInBotPilot(pilot)
                        }
                        styledTd {
                            css {
                                fontSize = 18.px
                                fontWeight = FontWeight("700")
                            }
                            +pilot.name
                        }
                        styledTd {
                            css {
                                fontSize = 18.px
                                fontFamily = "Roboto Mono"
                                fontWeight = FontWeight("800")
                            }
                            if (lapTime != null) {
                                +lapTime.format()
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

private fun StyledDOMBuilder<TD>.numberInBotPilot(pilot: Pilot) {
    numberInBox(pilot.number, Color(pilot.team.color), Color.black)
}

private fun StyledDOMBuilder<TD>.numberInBox(
    number: Number,
    backgroundColor: Color = Color.white,
    numberColor: Color = Color.black
) {
    styledDiv {
        css {
            val size = 30.px

            color = numberColor
            this.backgroundColor = backgroundColor
            borderRadius = 10.px
            width = size
            height = size
            lineHeight = LineHeight("$size")
            fontSize = 18.px
            fontFamily = "Roboto Mono"
            fontWeight = FontWeight("900")
            marginLeft = LinearDimension.auto
            marginRight = LinearDimension.auto
        }
        +"$number"
    }
}
