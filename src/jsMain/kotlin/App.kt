import io.live.timing.Pilot
import kotlinext.js.jsObject
import react.PropsWithChildren
import react.RBuilder
import react.RComponent
import react.*
import react.dom.div
import react.dom.h1
import react.dom.h3
import react.dom.h5

external interface AppState : State {
    var running: String
}

class App : RComponent<PropsWithChildren, AppState>() {

    override fun AppState.init() {
    }

    override fun RBuilder.render() {
        val maxVerstappen = Pilot("Max Verstappen")

        h1 {
            +"Kotlin LiveTiming"
        }
        div {
            h3 {
                +"Tableau des temps"
            }
            h5 {
                +maxVerstappen.name
            }
        }
    }
}