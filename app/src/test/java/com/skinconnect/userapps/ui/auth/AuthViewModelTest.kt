package com.skinconnect.userapps.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.util.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class AuthViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: AuthRepository
    lateinit var viewModel: AuthViewModel
    var dummyMessage = DataDummy.generateDummyMessage()

    @Before
    abstract fun setUp()

    @Test
    abstract fun `when Network Error Should Return Error`()
}