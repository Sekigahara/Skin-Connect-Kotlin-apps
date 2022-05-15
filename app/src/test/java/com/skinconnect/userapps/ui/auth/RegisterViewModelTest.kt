package com.skinconnect.userapps.ui.auth

import androidx.lifecycle.MutableLiveData
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.util.DataDummy
import com.skinconnect.userapps.util.getOrAwaitValue
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class RegisterViewModelTest : AuthViewModelTest() {
    private var dummyRequest = DataDummy.generateDummyRegisterRequest()

    override fun setUp() {
        viewModel = RegisterViewModel(repository)
    }

    override fun `when Network Error Should Return Error`() {
        val viewModel = viewModel as RegisterViewModel
        val expectedMessage = MutableLiveData<Result<String>>()
        expectedMessage.value = Result.Error("Error")
        Mockito.`when`(viewModel.register(dummyRequest)).thenReturn(expectedMessage)
        val actualMessage = viewModel.register(dummyRequest).getOrAwaitValue()
        Mockito.verify(repository).register(dummyRequest)
        Assert.assertNotNull(actualMessage)
        Assert.assertTrue(actualMessage is Result.Error)
    }

    @Test
    fun `when register Should Not Null And Return Success`() {
        val viewModel = viewModel as RegisterViewModel
        val expectedMessage = MutableLiveData<Result<String>>()
        expectedMessage.value = Result.Success(dummyMessage)
        Mockito.`when`(viewModel.register(dummyRequest)).thenReturn(expectedMessage)
        val actualMessage = viewModel.register(dummyRequest).getOrAwaitValue()
        Mockito.verify(repository).register(dummyRequest)
        Assert.assertNotNull(actualMessage)
        Assert.assertTrue(actualMessage is Result.Success)
        Assert.assertEquals(dummyMessage, (actualMessage as Result.Success).data)
    }
}