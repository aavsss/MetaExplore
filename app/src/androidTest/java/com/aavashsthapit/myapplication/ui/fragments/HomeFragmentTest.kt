package com.aavashsthapit.myapplication.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.adapters.BaseStreamersAdapter
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.data.entity.Streamer
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

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var streamersAdapter : StreamersAdapter

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun homeFragment_navigate_to_DetailFragment(){
        streamersAdapter.apply {
            streamers = FakeRepo.testStreamers
        }

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            binding = FragmentHomeBinding.bind(requireView()) //viewBinding
            binding.rvAllStreamers.apply {
                adapter = streamersAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            println(binding.rvAllStreamers)
            Navigation.setViewNavController(requireView(), navController)
        }

        //Mock click on recyclerView
        Espresso.onView(withId(R.id.rv_all_streamers))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BaseStreamersAdapter.StreamerViewHolder>(0, click()))
//        Espresso.onView(withId(R.id.fabAddShoppingItem)).perform(ViewActions.click())
//
//        verify(navController).navigate(
//            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
//        )
    }




}