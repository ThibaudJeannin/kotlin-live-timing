package com.ergast

import io.live.timing.Constructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
internal class ErgastAPIClientTest {

    lateinit var apiClient: ErgastAPIClient

    @BeforeTest
    internal fun setUp() {
        apiClient = ErgastAPIClient()
    }

    @Test
    fun testGetQualifying() = runTest {
        val response = apiClient.getQualifyingResult()
        println(response)
    }

    @Test
    fun testGetConstructors() = runTest {
        val response = apiClient.getConstructors()
        for (constructor in response.MRData.ConstructorTable.Constructors) {
            println(constructor)
        }
    }

}