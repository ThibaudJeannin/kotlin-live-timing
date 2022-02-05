package io.live.timing

import kotlinx.serialization.Serializable

@Serializable
class Race(
    val name: String,
    val id: String
)