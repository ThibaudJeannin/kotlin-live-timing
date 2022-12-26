package data

import io.live.timing.ChronoLap
import io.live.timing.Pilot
import io.live.timing.Race
import io.live.timing.TimeBoard

interface LiveTimingDataProvider {

    suspend fun getPilots(): List<Pilot>
    suspend fun getLaps(): List<ChronoLap>
    suspend fun getTimeBoard(raceId: String): TimeBoard
    suspend fun getRaces(): List<Race>
}