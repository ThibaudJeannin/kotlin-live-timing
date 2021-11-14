package io.live.timing

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TimeBoardTest {

    lateinit var timeBoard: TimeBoard

    @BeforeTest
    internal fun setUp() {
        timeBoard = TimeBoard(TestData.pilots)
    }

    @Test
    fun updateTimeBoard_noLaps() {
        val expectedBestLaps = mapOf(TestData.ver to null, TestData.per to null, TestData.rus to null)
        assertEquals(expectedBestLaps, timeBoard.bestLaps)
    }

    @Test
    internal fun testTimeBoard_withLaps() {
        timeBoard.insertLapTime(ChronoLap(TestData.ver, LapTime(1, 21)))
        timeBoard.insertLapTime(ChronoLap(TestData.ver, LapTime(1, 20)))
        timeBoard.insertLapTime(ChronoLap(TestData.rus, LapTime(1, 22), false))
        timeBoard.insertLapTime(ChronoLap(TestData.rus, LapTime(1, 21), false))
        timeBoard.insertLapTime(ChronoLap(TestData.rus, LapTime(1, 19), false))
        timeBoard.updateTimeBoard()
        val expectedBestLaps =
            mapOf(TestData.ver to LapTime(1, 20), TestData.per to null, TestData.rus to null)
        assertEquals(expectedBestLaps, timeBoard.bestLaps)
    }
}