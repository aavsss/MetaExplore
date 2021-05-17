package com.aavashsthapit.myapplication.ui.fragments

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.filters.MediumTest
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.adapters.BaseStreamersAdapter
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.databinding.FragmentHomeBinding
import com.aavashsthapit.myapplication.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import org.mockito.Mockito.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.aavashsthapit.myapplication.getOrAwaitValueAndroidTest
import com.aavashsthapit.myapplication.other.Resource
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Matchers.allOf
import java.util.*
import java.util.regex.Matcher

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var streamersAdapterForPractice : StreamersAdapter

    @Inject
    lateinit var testFragmentFactory: TestStreamersFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
        streamersAdapterForPractice.streamers = FakeRepo.testStreamers
    }

    @Test
    fun isAppNameVisible() {
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
        }
        onView(withId(R.id.app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun isSearchViewVisible() {
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
        }
        onView(withId(R.id.sv_search_streamers)).check(matches(isDisplayed()))
    }

    @Test
    fun isCategoryNameVisible() {
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
        }
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
    }

    @Test
    fun isRecyclerViewVisible() {
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
            streamersAdapter.apply {
                streamers = FakeRepo.testStreamers
            }

            binding = FragmentHomeBinding.bind(requireView()) //viewBinding
            binding.rvAllStreamers.apply {
                adapter = streamersAdapter
                layoutManager = LinearLayoutManager(requireContext()) //requireContext error here?
            }
        }
        onView(withId(R.id.rv_all_streamers)).check(matches(isDisplayed()))
    }

    @Test
    fun scrollingInRecyclerView() {
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
            streamersAdapter.apply {
                streamers = FakeRepo.testStreamers
            }

            binding = FragmentHomeBinding.bind(requireView()) //viewBinding
            binding.rvAllStreamers.apply {
                adapter = streamersAdapter
                layoutManager = LinearLayoutManager(requireContext()) //requireContext error here?
            }
        }

        onView(withId(R.id.rv_all_streamers))
                .perform(
                        RecyclerViewActions.scrollToPosition<BaseStreamersAdapter.StreamerViewHolder>(
                                0
                        )
                )
    }

    @Test
    fun homeFragment_navigate_to_DetailFragment(){
        //Mocking navController
        val navController = mock(NavController::class.java)

        //Launching Fragment contained in Hilt
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {

            //Populating streamers. Was having error here when it was above launch Fragment
            streamersAdapter.apply {
                streamers = FakeRepo.testStreamers
            }

            //Instantiating binding
            binding = FragmentHomeBinding.bind(requireView()) //viewBinding
            binding.rvAllStreamers.apply {
                adapter = streamersAdapter
                layoutManager = LinearLayoutManager(requireContext()) //requireContext error here?
            }

            //Setting mock nav controller
            Navigation.setViewNavController(
                    requireView(),
                    navController
            )
        }

        //Mock click on recyclerView
        onView(withId(R.id.rv_all_streamers))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<BaseStreamersAdapter.StreamerViewHolder>(
                                2,
                                click()
                        )
                )

        //Verify that we navigated to the correct place
        verify(navController).navigate(
                R.id.action_homeFragment_to_detailFragment
        )
    }

    @Test
    fun correctImageDisplayedInView() {
        val imageUrl = "https://specials-images.forbesimg.com/imageserve/5f5f55887d9eec237a586841/960x0.jpg?fit=scale"
        //Need to write a Fragment Factory class to perform this
        var testViewModel: MainViewModel? = null
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
            testViewModel = mainViewModel
            testViewModel?.setTestStreamers(FakeRepo.testStreamers)
            streamersAdapter.apply {
                streamers = FakeRepo.testStreamers
            }

            //Instantiating binding
            binding = FragmentHomeBinding.bind(requireView()) //viewBinding
            binding.rvAllStreamers.apply {
                adapter = streamersAdapter
                layoutManager = LinearLayoutManager(requireContext()) //requireContext error here?
            }
        }

        assertThat(testViewModel?.streamers?.getOrAwaitValueAndroidTest()?.data?.get(0)?.thumbnail_url).contains(imageUrl)

    }

    @Test
    fun testingSearchCallback(){
        //Need to write a Fragment Factory class to perform this
        var testViewModel: MainViewModel? = null
        launchFragmentInHiltContainer<HomeFragment>(
                fragmentFactory = testFragmentFactory
        ) {
            testViewModel = mainViewModel
            streamersAdapter.apply {
                streamers = FakeRepo.testStreamers
            }

            //Instantiating binding
            binding = FragmentHomeBinding.bind(requireView()) //viewBinding
            binding.rvAllStreamers.apply {
                adapter = streamersAdapter
                layoutManager = LinearLayoutManager(requireContext()) //requireContext error here?
            }
        }

        onView(withId(R.id.sv_search_streamers)).perform(
                typeSearchViewText("poki")
        )

        println(testViewModel?.streamers?.getOrAwaitValueAndroidTest()?.data)
        assertThat(testViewModel?.streamers?.getOrAwaitValueAndroidTest()?.data).hasSize(1)
    }

    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "Change view text"
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }



}