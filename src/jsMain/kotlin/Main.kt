import kotlinx.browser.document
import react.dom.render

fun main() {
    document.body!!.insertAdjacentHTML("afterbegin", "<div id='root'></div>")
    render(document.getElementById("root")!!) {
        child(app)
    }
}