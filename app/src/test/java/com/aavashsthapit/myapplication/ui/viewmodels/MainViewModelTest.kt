package com.aavashsthapit.myapplication.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.Rule

class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : MainViewModel

    @Before
    fun setup(){
        viewModel = MainViewModel(FakeRepo)
    }

    @Test
    fun `TenZ is the current streamer`() {
        val tenZ = FakeRepo.testStreamers.first {
            it.display_name == "TenZ"
        }
        viewModel.setCurrentStreamer(tenZ)
        assertThat(viewModel.currentStreamer.value?.data == tenZ)
    }
}