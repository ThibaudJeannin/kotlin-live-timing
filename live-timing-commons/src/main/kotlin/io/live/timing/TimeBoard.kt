package io.live.timing

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class TimeBoard(private val pilots: List<Pilot>) {

    private val allLaps: MutableList<ChronoLap>
    var bestLaps: Map<Pilot, Duration?>

    init {
        allLaps = ArrayList()
        bestLaps = HashMap()
        this.updateTimeBoard()
    }


    fun insertLapTime(lapTime: ChronoLap) {
        allLaps.add(lapTime)
    }

    fun updateTimeBoard() {
        val updatedBestLaps: MutableMap<Pilot, Duration?> = HashMap()
        for (pilot in pilots) {
            val bestLapForPilot = allLaps.filter { it.pilot == pilot && it.valid }.minByOrNull { chronoLap -> chronoLap.lapTime }
            updatedBestLaps[pilot] = bestLapForPilot?.lapTime

        }
        bestLaps = updatedBestLaps
    }

}