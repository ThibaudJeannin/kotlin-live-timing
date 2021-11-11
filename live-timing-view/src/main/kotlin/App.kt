import react.PropsWithChildren
import react.RBuilder
import react.RComponent
import react.*
import react.dom.div
import react.dom.h1
import react.dom.h3

external interface AppState : State {
    var idle : String
    var running : String
}

@ExperimentalJsExport
@JsExport
class App : RComponent<PropsWithChildren, AppState>() {

    override fun RBuilder.render() {
        h1 {
            +"Kotlin LiveTiming"
        }
        div {
            h3 {
                +"Oui"
            }
        }

    }
}