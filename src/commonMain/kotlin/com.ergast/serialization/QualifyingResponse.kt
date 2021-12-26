package com.ergast.serialization

import kotlinx.serialization.Serializable

@Serializable
data class QualifyingResponse(
    val MRData: QualifyingMRData
)

@Serializable
data class QualifyingMRData(
    val series: String,
    val url: String
)
