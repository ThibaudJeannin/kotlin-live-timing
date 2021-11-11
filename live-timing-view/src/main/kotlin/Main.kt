import kotlinx.browser.document
import react.dom.render

class Main {

    fun main() {
        render(document.getElementById("root")) {
            child(App::class) {}
        }
    }
}