import io.live.timing.ChronoLap
import io.live.timing.Pilot

interface LiveTimingDataProvider {

    suspend fun getPilots(): List<Pilot>
    suspend fun getLaps(): List<ChronoLap>
}