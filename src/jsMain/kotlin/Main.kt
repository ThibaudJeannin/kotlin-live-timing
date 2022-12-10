import dom.Element
import dom.html.HTML.div
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
    document.body!!.insertAdjacentHTML("afterbegin", "<div id='root'></div>")
    val container = document.getElementById("root")!! as Element
    createRoot(container).render(app.create())
}