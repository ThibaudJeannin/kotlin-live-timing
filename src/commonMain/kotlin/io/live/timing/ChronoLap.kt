package io.live.timing

import kotlinx.serialization.Serializable

@Serializable
data class ChronoLap(val pilot: Pilot, val lapTime: LapTime, var valid: Boolean = true)