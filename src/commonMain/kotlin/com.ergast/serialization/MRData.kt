package com.ergast.serialization

import kotlinx.serialization.Serializable

@Serializable
data class MRData2(
    val series: String,
    val url: String
)
