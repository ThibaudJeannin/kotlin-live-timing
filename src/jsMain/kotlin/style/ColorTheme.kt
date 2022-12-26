package style

enum class ColorTheme(val cssClassName: String) {
    LIGHT("light-theme"),
    DARK("dark-theme");

    fun toggle(): ColorTheme {
        return if (this == DARK) {
            LIGHT
        } else {
            DARK
        }
    }
}