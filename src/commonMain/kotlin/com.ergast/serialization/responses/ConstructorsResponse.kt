package com.ergast.serialization.responses

import com.ergast.serialization.Constructor
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorsResponse(
    val MRData: ConstructorsMRData
)

@Serializable
data class ConstructorsMRData(
    val ConstructorTable: ConstructorTable
)

@Serializable
data class ConstructorTable(
    val Constructors: List<Constructor>
)