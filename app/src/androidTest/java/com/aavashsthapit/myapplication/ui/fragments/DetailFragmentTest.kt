package com.aavashsthapit.myapplication.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<DetailFragment> {
        }
    }

    @Test
    fun isNameVisible() {
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
    }

    @Test
    fun isImageViewVisible() {
        onView(withId(R.id.iv_streamer_img)).check(matches(isDisplayed()))
    }

    @Test
    fun isCategoryVisible() {
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
    }
}
