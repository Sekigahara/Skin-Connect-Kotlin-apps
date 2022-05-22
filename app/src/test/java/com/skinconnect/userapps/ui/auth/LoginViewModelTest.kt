package com.skinconnect.userapps.ui.auth

import com.skinconnect.userapps.util.DataDummy
import org.junit.Test

class LoginViewModelTest : AuthViewModelTest() {
    private val dummyRequest = DataDummy.generateDummyLoginRequest()

    override fun setUp() {
        viewModel = LoginViewModel(repository)
    }

    override fun `when Network Error Should Return Error`() {
//        val viewModel = viewModel as LoginViewModel
//        val expectedMessage = MutableLiveData<Result>()
//        expectedMessage.value = Result.Error("Error")
//        `when`(viewModel.login(dummyRequest)).thenReturn(expectedMessage)
//        val actualMessage = viewModel.login(dummyRequest).getOrAwaitValue()
//        Mockito.verify(repository).login(dummyRequest)
//        Assert.assertNotNull(actualMessage)
//        Assert.assertTrue(actualMessage is Result.Error)
    }

    @Test
    fun `when Login Should Not Null and Return Success`() {
//        val viewModel = viewModel as LoginViewModel
//        val expectedMessage = MutableLiveData<Result>()
//        expectedMessage.value = Result.Success(dummyMessage)
//        `when`(viewModel.login(dummyRequest)).thenReturn(expectedMessage)
//        val actualMessage = viewModel.login(dummyRequest).getOrAwaitValue()
//        Mockito.verify(repository).login(dummyRequest)
//        Assert.assertNotNull(actualMessage)
//        Assert.assertTrue(actualMessage is Result.Success<*>)
//        Assert.assertEquals(dummyMessage, (actualMessage as Result.Success<*>).data)
    }
}