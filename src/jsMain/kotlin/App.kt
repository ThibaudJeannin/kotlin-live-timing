import browser.document
import data.ErgastDataProvider
import io.live.timing.LapTime
import io.live.timing.Pilot
import io.live.timing.Race
import io.live.timing.TimeBoard
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.TD
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLSelectElement
import react.*
import react.dom.form
import react.dom.h3
import react.dom.onClick
import react.dom.tr
import style.*
import styled.*

private val scope = MainScope()

val app = fc<PropsWithChildren> {
    val dataProvider = ErgastDataProvider()
    var selectedRound = "22"
    var timeBoard by useState(TimeBoard(emptyList()))
    var races: List<Race> by useState(emptyList())
    var theme: ColorTheme by useState(ColorTheme.LIGHT)

    useEffectOnce {
        scope.launch {
            races = dataProvider.getRaces()
            val newTimeBoard = dataProvider.getTimeBoard(selectedRound)
            newTimeBoard.updateTimeBoard()
            timeBoard = newTimeBoard
        }
    }

    useEffect {
        ColorTheme.values().forEach { colorTheme -> document.body.classList.remove(colorTheme.cssClassName) }
        document.body.classList.add(theme.cssClassName)
    }

    styledDiv {
        css {
            height = 120.px
        }
        styledDiv {
            attrs.onClick = {
                theme = theme.toggle()
            }
            css {
                backgroundColor = F1_WARM_RED
                color = F1_HIGH_VIS_WHITE
                position = Position.relative
                float = Float.left
                height = 100.pct
                width = 120.px
                borderRadius = 12.px
                borderStyle = BorderStyle.none
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
        styledDiv {
            css {
                position = Position.relative
                float = Float.left
                height = 100.pct
                display = Display.flex
                alignItems = Align.center
                boxSizing = BoxSizing.borderBox
                paddingLeft = 10.px
                paddingRight = 10.px
            }
            form {
                styledLabel {
                    +"Grand Prix"
                    css {
                        paddingRight = 10.px
                        fontSize = 18.px
                        fontWeight = FontWeight("700")
                    }
                }
                styledSelect {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLSelectElement
                        println(target.value)
                        selectedRound = target.value

                        scope.launch {
                            val newTimeBoard = dataProvider.getTimeBoard(selectedRound)
                            newTimeBoard.updateTimeBoard()
                            timeBoard = newTimeBoard
                        }
                    }
                    css {
                        fontSize = 16.px
                        borderStyle = BorderStyle.none
                        margin(0.px)
                        padding(0.em, 1.em, 0.em, 0.em)
                        width = 100.pct
                        height = 35.px
                        backgroundColor = F1_HIGH_VIS_WHITE
                        color = F1_CARBON_BLACK_70
                    }
                    races.forEach {
                        styledOption {
                            attrs.value = it.id
                            +it.name
                            if (it.id == selectedRound.toString()) {
                                attrs.selected = true
                            }

                            css {
                                color = F1_CARBON_BLACK
                                backgroundColor = F1_HIGH_VIS_WHITE
                                display = Display.flex
                                minHeight = 20.px
                                padding(0.px, 2.px, 1.px, 0.px)
                            }
                        }
                    }
                }
            }
        }
        styledDiv {
            css {
                position = Position.relative
                float = Float.right
                height = 100.pct
                lineHeight = LineHeight(30.px.toString())
                fontWeight = FontWeight("900")
                fontSize = 16.pt
                paddingRight = 10.px
                display = Display.flex
                alignItems = Align.center
                boxSizing = BoxSizing.borderBox
            }
            h3 {
                +"Timeboard"
            }
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
                        +"constructor"
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
                    styledTr {
                        css {
                            textAlign = TextAlign.center

                            height = 40.px
                            padding(5.px)

                            classes.add(
                                when {
                                    i % 2 == 0 -> "alternateA"
                                    else -> "alternateB"
                                }
                            )
                            borderStyle = BorderStyle.dashed
                            borderLeftStyle = BorderStyle.none
                            borderRightStyle = BorderStyle.none
                            borderRadius = 10.px
                            borderWidth = 2.px
                        }
                        styledTd {
                            numberInBox(i + 1) {
                                classes.add("number-position")
                                if (i == 0) {
                                    classes.add("purple")
                                }
                            }
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
                                fontWeight = FontWeight("700")
                            }
                            +pilot.team.name
                        }
                        styledTd {
                            css {
                                if (i == 0 && lapTime != null) {
                                    color = F1_PURPLE
                                }
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
                    i++
                }
            }
        }
    }
}

private fun StyledDOMBuilder<TD>.numberInBotPilot(pilot: Pilot) {
    numberInBox(pilot.number) {
        color = getForegroundColorForBackgroundColor(pilot.team.color)
        backgroundColor = Color(pilot.team.color)
    }
}

private fun getForegroundColorForBackgroundColor(color: String): Color {
    val redChannel = color.substring(1, 3).toInt(16)
    val greenChannel = color.substring(3, 5).toInt(16)
    val blueChannel = color.substring(5, 7).toInt(16)
    val brightness = intArrayOf(redChannel, greenChannel, blueChannel).average()
    return if (brightness > 120) Color.black else Color.white
}

private fun StyledDOMBuilder<TD>.numberInBox(
    number: Number,
    additionalCSS: CssBuilder.() -> Unit
) {
    styledDiv {
        css {
            val size = 30.px
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
        css(additionalCSS)
        +"$number"
    }
}
