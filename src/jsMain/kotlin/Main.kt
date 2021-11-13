import io.live.timing.Pilot
import kotlinx.browser.document
import react.dom.render
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    render(document.getElementById("root")) {
        console.log("render")
        child(App::class) {}
    }
}