import io.live.timing.Constructor
import io.live.timing.Pilot

val redBull = Constructor("Red Bull Racing", "#0600EF")
val mercedes = Constructor("Mercedes", "#00D2BE")
val ferrari = Constructor("Ferrari", "#DC0000")
val mcLaren = Constructor("McLaren", "#FF8700")
val alpine = Constructor("Alpine", "#0090FF")
val alphaTauri = Constructor("Alpha Tauri", "#2B4562")
val astonMartin = Constructor("Aston Martin", "#006F62")
val williams = Constructor("Williams", "#005AFF")
val alfaRomeo = Constructor("Alfa Romeo", "#900000")
val haas = Constructor("Haas", "#FFFFFF")

val constructors : List<Constructor> = listOf(
    redBull,
    mercedes,
    mcLaren,
    ferrari,
    alphaTauri,
    alpine,
    astonMartin,
    williams,
    alfaRomeo,
    haas,
)

val pilots : List<Pilot> = listOf(
    Pilot("Max Verstappen", 33, redBull),
    Pilot("Valtteri Bottas", 77, mercedes),
    Pilot("Charles Leclerc", 16, ferrari),
    Pilot("Lando Norris", 4, mcLaren),
    Pilot("Sebastian Vettel", 5, astonMartin),
    Pilot("Pierre Gasly", 10, alphaTauri),
    Pilot("Fernando Alonso", 14, alpine),
    Pilot("George Russell", 63, williams),
    Pilot("Kimi Raikkonen", 7, alfaRomeo),
    Pilot("Mick Schumacher", 47, haas)
)