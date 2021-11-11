package io.live.timing

import io.live.timing.TestData.rus
import io.live.timing.TestData.ver
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class ChronoLapTest {

    @Test
    internal fun testChronoLap() {
        val lap1 = ChronoLap(ver, Duration.Companion.seconds(75))
        val lap2 = ChronoLap(rus, Duration.Companion.seconds(76))
        val lap3 = ChronoLap(ver, Duration.Companion.seconds(75), false)
        val lap4 = ChronoLap(ver, Duration.Companion.seconds(76))

        assertNotEquals(lap1, lap2)
        assertNotEquals(lap1, lap4)
        assertTrue(lap1.valid)
        assertFalse(lap3.valid)
    }
}