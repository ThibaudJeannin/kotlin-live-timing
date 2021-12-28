import com.ergast.ErgastAPIClient
import com.ergast.serialization.QualifyingResults
import com.ergast.serialization.responses.Driver
import io.live.timing.*

class ErgastDataProvider : LiveTimingDataProvider {

    private val ergastApiClient = ErgastAPIClient()

    override suspend fun getPilots(): List<Pilot> {
        val ergastDrivers = ergastApiClient.getDrivers()
        println(ergastDrivers)
        return ergastDrivers.MRData.DriverTable.Drivers.map {
            convertErgastDriverToPilot(it)
        }
    }

    private suspend fun convertErgastDriverToPilot(driver: Driver): Pilot {
        val constructorsForDriver =
            ergastApiClient.getConstructorsForDriver(driver.driverId).MRData.ConstructorTable.Constructors.last()
        val constructor = convertErgastConstructor(constructorsForDriver)
        return convertErgastDriverToPilot(driver, constructor)
    }

    private fun convertErgastConstructor(constructor: com.ergast.serialization.responses.Constructor): Constructor {
        val constructorColor = constructorColors[constructor.constructorId]
        return Constructor(constructor.name, constructorColor ?: "#4c4c4c")
    }

    private fun convertErgastDriverToPilot(driver: Driver, constructor: Constructor): Pilot {
        return Pilot("${driver.givenName} ${driver.familyName}", driver.permanentNumber, constructor)

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

    override suspend fun getTimeBoard(): TimeBoard {
        val qualifyingResults = ergastApiClient.getQualifyingResult().MRData.RaceTable.Races[0].QualifyingResults
        val timeBoard = TimeBoard(qualifyingResults!!.map { getPilotFromResult(it) })
        qualifyingResults!!.forEach {
            val qualifyingLapTime: LapTime
            if (it.Q3 != null) {
                qualifyingLapTime = convertErgastTimeToChronoLap(it.Q3!!)
            } else if (it.Q2 != null) {
                qualifyingLapTime = convertErgastTimeToChronoLap(it.Q2!!)
            } else {
                qualifyingLapTime = convertErgastTimeToChronoLap(it.Q1!!)
            }

            val chronoLap = ChronoLap(
                getPilotFromResult(it),
                qualifyingLapTime
            )
            timeBoard.insertLapTime(chronoLap)
        }
        return timeBoard

    }

    private fun getPilotFromResult(it: QualifyingResults): Pilot {
        val constructor = convertErgastConstructor(it.Constructor)
        return convertErgastDriverToPilot(it.Driver, constructor)
    }

    private fun convertErgastTimeToChronoLap(time: String): LapTime {
        val splitTime = time.split(".", ":")
        return LapTime(splitTime[0].toInt(), splitTime[1].toInt(), splitTime[2].toInt())
    }
}