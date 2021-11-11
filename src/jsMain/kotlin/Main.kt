import io.live.timing.Pilot
import kotlinx.browser.document
import react.dom.render

fun main() {
    render(document.getElementById("root")) {
        console.log("render")
        child(App::class) {}
    }
}