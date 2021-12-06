package io.live.timing

class TimeBoard(val pilots: List<Pilot>) {

    val allLaps: MutableList<ChronoLap>
    var bestLaps: Map<Pilot, LapTime?>

    init {
        allLaps = ArrayList()
        bestLaps = HashMap()
        this.updateTimeBoard()
    }

    fun insertLapTime(lapTime: ChronoLap) {
        allLaps.add(lapTime)
    }

    fun insertLapTimes(vararg lapTime: ChronoLap) {
        allLaps.addAll(lapTime)
    }

    fun updateTimeBoard() {
        val updatedBestLaps: MutableMap<Pilot, LapTime?> = HashMap()
        for (pilot in pilots) {
            val bestLapForPilot =
                allLaps.filter { it.pilot == pilot && it.valid }.minByOrNull { chronoLap -> chronoLap.lapTime }
            updatedBestLaps[pilot] = bestLapForPilot?.lapTime

        }
        bestLaps = updatedBestLaps
    }

    fun sortedResults(): List<Map.Entry<Pilot, LapTime?>> {
        return bestLaps.entries.sortedWith(compareBy(nullsLast()) { it.value })
    }

}