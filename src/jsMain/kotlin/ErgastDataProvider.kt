import com.ergast.ErgastAPIClient
import com.ergast.serialization.responses.Driver
import io.live.timing.ChronoLap
import io.live.timing.Constructor
import io.live.timing.LapTime
import io.live.timing.Pilot

class ErgastDataProvider : LiveTimingDataProvider {

    private val ergastApiClient = ErgastAPIClient()

    override suspend fun getPilots(): List<Pilot> {
        val ergastDrivers = ergastApiClient.getDrivers()
        println(ergastDrivers)
        return ergastDrivers.MRData.DriverTable.Drivers.map {
            convertErgastDriverToPilot(it)
        }
    }

    private suspend fun convertErgastDriverToPilot(it: Driver): Pilot {
        val constructorsForDriver =
            ergastApiClient.getConstructorsForDriver(it.driverId).MRData.ConstructorTable.Constructors.last()
        val constructorColor = constructorColors[constructorsForDriver.constructorId]
        val constructor = Constructor(constructorsForDriver.name, constructorColor ?: "#4c4c4c")
        return Pilot("${it.givenName} ${it.familyName}", it.permanentNumber, constructor)
    }

    private val constructorColors = mapOf(
        "red_bull" to "#0600EF",
        "mercedes" to "#00D2BE",
        "ferrari" to "#DC0000",
        "mclaren" to "#FF8700",
        "alpine" to "#0090FF",
        "alphatauri" to "#2B4562",
        "aston_martin" to "#006F62",
        "williams" to "#005AFF",
        "alfa" to "#900000",
        "haas" to "#FFFFFF"
    )

    override suspend fun getLaps(): List<ChronoLap> {
        return ergastApiClient.getQualifyingResult().MRData.RaceTable.Races[0].QualifyingResults!!.map {
            println(it)

            val qualifyingLapTime: LapTime

            if (it.Q3 != null) {
                qualifyingLapTime = convertErgastTimeToChronoLap(it.Q3!!)
            } else if (it.Q2 != null) {
                qualifyingLapTime = convertErgastTimeToChronoLap(it.Q2!!)
            } else {
                qualifyingLapTime = convertErgastTimeToChronoLap(it.Q1!!)
            }

            ChronoLap(
                convertErgastDriverToPilot(it.Driver),
                qualifyingLapTime
            )
        }
    }

    private fun convertErgastTimeToChronoLap(time: String): LapTime {
        val splitTime = time.split(".", ":")
        return LapTime(splitTime[0].toInt(), splitTime[1].toInt(), splitTime[2].toInt())
    }
}