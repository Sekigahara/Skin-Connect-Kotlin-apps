package com.skinconnect.userapps.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.util.DataDummy
import com.skinconnect.userapps.util.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: LoginViewModel
    private val dummyMessage = DataDummy.generateDummyMessage()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `when Login Should Not Null and Return Success`() {
        val request = LoginRequest("payjoo23@gmail.com", "payjoo456)(*&")
        val expectedMessage = MutableLiveData<Result<String>>()
        expectedMessage.value = Result.Success(dummyMessage)
        `when`(viewModel.login(request)).thenReturn(expectedMessage)
        val actualMessage = viewModel.login(request).getOrAwaitValue()
        Mockito.verify(repository).login(request)
        Assert.assertNotNull(actualMessage)
        Assert.assertTrue(actualMessage is Result.Success)
        Assert.assertEquals(dummyMessage, (actualMessage as Result.Success).data)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val request = LoginRequest("payjoo23@gmail.com", "payjoo456)(*&")
        val expectedMessage = MutableLiveData<Result<String>>()
        expectedMessage.value = Result.Error("Error")
        `when`(viewModel.login(request)).thenReturn(expectedMessage)
        val actualMessage = viewModel.login(request).getOrAwaitValue()
        Mockito.verify(repository).login(request)
        Assert.assertNotNull(actualMessage)
        Assert.assertTrue(actualMessage is Result.Error)
    }
}