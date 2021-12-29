package com.ergast.serialization

import kotlinx.serialization.Serializable

@Serializable
data class RaceTable(
    val season: String,
    val Races: List<Race>
)

@Serializable
data class Race(
    val raceName: String,
    val date: String,
    val time: String,
    val QualifyingResults: List<QualifyingResults>? = null,
    val Results: List<Results>? = null
)

@Serializable
data class QualifyingResults(
    val number: String,
    val position: String,
    val Driver: Driver,
    val Constructor: Constructor,
    val Q1: String? = null,
    val Q2: String? = null,
    val Q3: String? = null
)

@Serializable
data class Results(
    val number: String,
    val position: String,
    val positionText: String,
    val points: String,
    val Driver: Driver,
    val Constructor: Constructor,
    val grid: String,
    val laps: String,
    val status: String,
    val FastestLap: Lap
)

@Serializable
data class Lap(
    val rank: String,
    val lap: String,
    val Time: Time
)

@Serializable
data class Time(
    val time: String
)
