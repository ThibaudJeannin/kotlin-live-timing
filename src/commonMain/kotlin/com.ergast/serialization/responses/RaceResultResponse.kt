package com.ergast.serialization.responses

import com.ergast.serialization.RaceTable
import kotlinx.serialization.Serializable

@Serializable
data class RaceResultResponse(
    val MRData: RaceResultMRData
)

@Serializable
data class RaceResultMRData(
    val RaceTable: RaceTable
)

