package io.live.timing

import kotlinx.serialization.Serializable

@Serializable
data class LapTime(val minutes : Int, val seconds : Int, val millis : Int = 0) : Comparable<LapTime> {
    override fun compareTo(other: LapTime): Int {
        if (this.minutes > other.minutes) {
            return 1
        }
        if (this.minutes < other.minutes) {
            return -1
        }
        if (this.seconds > other.seconds) {
            return 1
        }
        if (this.seconds < other.seconds) {
            return -1
        }
        if (this.millis > other.millis) {
            return 1
        }
        if (this.millis < other.millis) {
            return -1
        }
        return 0
    }

    fun format() : String {
        return "${this.minutes}" +
                ":${this.seconds.toString().padStart(2, '0')}" +
                ".${this.millis.toString().padStart(3, '0')}"
    }

}