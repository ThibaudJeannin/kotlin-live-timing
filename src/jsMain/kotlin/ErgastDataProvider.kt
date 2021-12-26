import com.ergast.ErgastAPIClient
import io.live.timing.ChronoLap
import io.live.timing.Constructor
import io.live.timing.Pilot

class ErgastDataProvider : LiveTimingDataProvider {

    private val ergastApiClient = ErgastAPIClient()

    override suspend fun getPilots(): List<Pilot> {
        val ergastDrivers = ergastApiClient.getDrivers()
        return ergastDrivers.MRData.DriverTable.Drivers.map {
            val constructorsForDriver = ergastApiClient.getConstructorsForDriver(it.code)
            val constructor = Constructor(constructorsForDriver.MRData.ConstructorTable.Constructors.last().name, "#8c8c8c")

            Pilot("${it.givenName} ${it.familyName}", it.permanentNumber, constructor)
        }

    }

    override suspend fun getLaps(): List<ChronoLap> {
        return emptyList()
    }
}