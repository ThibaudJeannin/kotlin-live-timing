package io.live.timing

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
data class ChronoLap(val pilot : Pilot, val lapTime : Duration, var valid: Boolean = true)