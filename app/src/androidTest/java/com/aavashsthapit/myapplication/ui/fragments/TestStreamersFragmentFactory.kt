package com.aavashsthapit.myapplication.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.ui.fragments.homeFragment.HomeFragment
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel
import javax.inject.Inject

class TestStreamersFragmentFactory @Inject constructor(
        private val streamersAdapter: StreamersAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            HomeFragment::class.java.name -> HomeFragment(
                    streamersAdapter,
                    MainViewModel(FakeRepo)
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}