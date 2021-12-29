package com.ergast.serialization.responses

import com.ergast.serialization.Driver
import kotlinx.serialization.Serializable

@Serializable
data class DriversResponse(
    val MRData: PilotMRData
)

@Serializable
data class PilotMRData(
    val DriverTable: DriveTable
)

@Serializable
data class DriveTable(
    val Drivers: List<Driver>
)