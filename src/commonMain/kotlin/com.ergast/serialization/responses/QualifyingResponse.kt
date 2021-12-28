package com.ergast.serialization.responses

import com.ergast.serialization.RaceTable
import kotlinx.serialization.Serializable

@Serializable
data class QualifyingResponse(
    val MRData: QualifyingMRData
)

@Serializable
data class QualifyingMRData(
    val series: String,
    val url: String,
    val RaceTable: RaceTable
)
