package com.aavashsthapit.myapplication.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import javax.inject.Inject

class StreamersFragmentFactory @Inject constructor(
    private val streamersAdapter: StreamersAdapter,
    private val fakeRepo: FakeRepo
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(streamersAdapter, fakeRepo = fakeRepo)
            DetailFragment::class.java.name -> DetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
