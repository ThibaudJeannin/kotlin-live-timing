package com.ergast.serialization

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

@Serializable
data class Driver(
    val driverId: String,
    val permanentNumber: Int,
    val code: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String
)
