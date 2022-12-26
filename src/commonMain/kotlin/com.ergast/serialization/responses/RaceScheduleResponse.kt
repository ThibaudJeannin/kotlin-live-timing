package com.ergast.serialization.responses

import kotlinx.serialization.Serializable

@Serializable
data class RaceScheduleResponse(
    val MRData: RaceScheduleMRData
)

@Serializable
data class RaceScheduleMRData(
    val RaceTable: RaceTable
)

@Serializable
data class RaceTable(
    val season: String,
    val Races: List<Race>
)

@Serializable
data class Race(
    val season: String,
    val round: String,
    val raceName: String,
    val Circuit: Circuit
)

@Serializable
data class Circuit(
    val circuitId: String,
    val circuitName: String
)