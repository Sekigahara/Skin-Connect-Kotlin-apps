package com.skinconnect.userapps.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.retrofit.ApiService
import com.skinconnect.userapps.util.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService
    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        service = FakeApiService()
        repository = AuthRepository(service)
    }

    @Test
    fun `when login Should Not Null`() = runBlockingTest {
        val expectedResponse = DataDummy.generateDummyBaseResponse()
        val request = LoginRequest("payjoo23@gmail.com", "payjoo456)(*&")
        val actualResponse = service.login(request)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }
}