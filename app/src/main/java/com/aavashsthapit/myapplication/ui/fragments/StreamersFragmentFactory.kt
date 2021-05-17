package com.aavashsthapit.myapplication.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import javax.inject.Inject

class StreamersFragmentFactory @Inject constructor(
        private val streamersAdapter: StreamersAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            HomeFragment::class.java.name -> HomeFragment(streamersAdapter)
            DetailFragment::class.java.name -> DetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}