package com.ergast.serialization

import kotlinx.serialization.Serializable

@Serializable
data class Constructor(
    val constructorId: String,
    val url: String,
    val name: String,
)