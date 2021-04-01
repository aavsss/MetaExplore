package com.aavashsthapit.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.databinding.FragmentHomeBinding
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home){

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var streamersAdapter: StreamersAdapter

    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view) //viewBinding
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        subscribeStreamAdapterToFakeRepo()

        streamersAdapter.setItemClickListener {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }

        //Filter based on search query
        binding.svSearchStreamers.setOnQueryTextListener(mainViewModel.searchCallback)
    }

    private fun setupRecyclerView() = binding.rvAllStreamers.apply {
        adapter = streamersAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeStreamAdapterToFakeRepo(){
        mainViewModel.streamers.observe(viewLifecycleOwner) {
            streamersAdapter.streamers = it
        }
    }
}