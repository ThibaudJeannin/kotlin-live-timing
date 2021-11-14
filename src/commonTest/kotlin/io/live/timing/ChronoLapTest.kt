package io.live.timing

import io.live.timing.TestData.rus
import io.live.timing.TestData.ver
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ChronoLapTest {

    @Test
    internal fun testChronoLap() {
        val lap1 = ChronoLap(ver, LapTime(1, 15, 0))
        val lap2 = ChronoLap(rus, LapTime(1, 16, 0))
        val lap3 = ChronoLap(ver, LapTime(1, 15, 0), false)
        val lap4 = ChronoLap(ver, LapTime(1, 16, 0))

        assertNotEquals(lap1, lap2)
        assertNotEquals(lap1, lap4)
        assertTrue(lap1.valid)
        assertFalse(lap3.valid)
    }
}