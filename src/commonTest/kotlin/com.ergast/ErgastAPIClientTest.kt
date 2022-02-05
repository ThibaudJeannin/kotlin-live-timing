package com.ergast

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
        val response = apiClient.getQualifyingResult(22)
        println(response)
    }

    @Test
    fun testGetConstructors() = runTest {
        val response = apiClient.getConstructors()
        for (constructor in response.MRData.ConstructorTable.Constructors) {
            println(constructor)
        }
    }

    @Test
    fun testGetDrivers() = runTest {
        val response = apiClient.getDrivers()
        for (driver in response.MRData.DriverTable.Drivers) {
            println(driver)
        }
    }

}