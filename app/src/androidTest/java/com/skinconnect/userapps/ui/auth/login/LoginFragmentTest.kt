package com.skinconnect.userapps.ui.auth.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.remote.ApiConfig
import com.skinconnect.userapps.data.repository.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.startsWith
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {
    private val mockWebServer = MockWebServer()
//    private val dummyEmail = "payjoo23@gmail.com"
    private val dummyEmail = "as@as.as"
//    private val dummyPassword = "payjoo456)(*&"
    private val dummyPassword = "asasasas"

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConfig.baseUrl = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun login_Success() {
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_UserSkinConnect)
//        val mockBody = JsonConverter.readStringFromFile("auth/login_success.json")
//        val mockResponse = MockResponse().setResponseCode(200).setBody(mockBody)
//        mockWebServer.enqueue(mockResponse)
        onView(withId(R.id.cv_email)).perform(typeText(dummyEmail), closeSoftKeyboard())
        onView(withId(R.id.cv_password)).perform(typeText(dummyPassword), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).perform(click())
//        onView(withText("Success")).check(matches(isDisplayed()))
    }

    @Test
    fun login_Error() {
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_UserSkinConnect)
//        val mockResponse = MockResponse().setResponseCode(500)
//        mockWebServer.enqueue(mockResponse)
        onView(withId(R.id.cv_email)).perform(typeText(dummyEmail), closeSoftKeyboard())
        onView(withId(R.id.cv_password)).perform(typeText(dummyPassword), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withText(startsWith("Something went wrong"))).check(matches(isDisplayed()))
    }
}