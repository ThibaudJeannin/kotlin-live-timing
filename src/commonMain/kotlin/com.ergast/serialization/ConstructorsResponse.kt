package com.ergast.serialization

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

@Serializable
data class Constructor(
    val constructorId: String,
    val url: String,
    val name: String,
)