package com.aavashsthapit.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.api.TwitchStreamersApi
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.databinding.FragmentHomeBinding
import com.aavashsthapit.myapplication.other.Extensions.isViewVisible
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Uses ViewBinding
 * Uses mainViewModel
 * Sets up Home Fragment with recyclerView and
 *         streamersAdapter
 * Uses onClickListener here where data is provided from StreamersAdapter
 * Uses setOnTextChangeQueryListener for search feature
 * streamersAdapter.streamers instantiated here in subScribeToFakeRepo
 */

/**
 * Error log bothering
 * IOException, you might not have internet connection timeout
 */
@AndroidEntryPoint
class HomeFragment @Inject constructor(
        val streamersAdapter: StreamersAdapter,
        var mainViewModel: MainViewModel? = null
) : Fragment(R.layout.fragment_home){

    @Inject
    lateinit var twitchStreamersApi: TwitchStreamersApi

    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view) //viewBinding
        mainViewModel = mainViewModel ?: ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        setUpScrollBehavior()
        subscribeStreamAdapterToFakeRepo()

        streamersAdapter.apply {
            setItemClickListener {
                mainViewModel?.setCurrentStreamer(it)
                findNavController().navigate(
                        R.id.action_homeFragment_to_detailFragment
                )
            }

            setItemLongClickListener {

                it.expanded = !it.expanded
                this.notifyItemChanged(streamers.indexOfFirst { streamer ->
                    streamer.display_name == it.display_name
                })
                return@setItemLongClickListener true
            }
        }

        mainViewModel?.sendHttpRequest(twitchStreamersApi)
        //If the connection is not made
        mainViewModel?.listener = {
            binding.allStreamersProgressBar.isVisible = false
        }
        //Filter based on search query
        binding.svSearchStreamers.setOnQueryTextListener(mainViewModel?.getSearchCallback(FakeRepo.streamers))

    }

    private fun setUpScrollBehavior() = binding.scvEntire.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
//        if (scrollY < oldScrollY && !binding.scvEntire.isViewVisible(binding.svSearchStreamers)) {
//            println(".. going up when search view is not visible")
//
//        }
    }

    private fun setupRecyclerView() = binding.rvAllStreamers.apply {
        adapter = streamersAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }


    private fun subscribeStreamAdapterToFakeRepo(){
        mainViewModel?.streamers?.observe(viewLifecycleOwner) {
            streamersAdapter.streamers = it.data!!
            binding.allStreamersProgressBar.visibility = View.GONE
        }
    }


}