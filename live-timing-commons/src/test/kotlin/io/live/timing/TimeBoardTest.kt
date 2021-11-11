package io.live.timing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class TimeBoardTest {

    lateinit var timeBoard: TimeBoard

    @BeforeEach
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
        timeBoard.insertLapTime(ChronoLap(TestData.ver, Duration.Companion.seconds(81)))
        timeBoard.insertLapTime(ChronoLap(TestData.ver, Duration.Companion.seconds(80)))
        timeBoard.insertLapTime(ChronoLap(TestData.rus, Duration.Companion.seconds(82), false))
        timeBoard.insertLapTime(ChronoLap(TestData.rus, Duration.Companion.seconds(81), false))
        timeBoard.insertLapTime(ChronoLap(TestData.rus, Duration.Companion.seconds(81), false))
        timeBoard.updateTimeBoard()
        val expectedBestLaps = mapOf(TestData.ver to Duration.Companion.seconds(80), TestData.per to null, TestData.rus to null)
        assertEquals(expectedBestLaps, timeBoard.bestLaps)
    }
}