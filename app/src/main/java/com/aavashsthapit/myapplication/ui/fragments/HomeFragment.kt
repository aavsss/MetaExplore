package com.aavashsthapit.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home){

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var streamersAdapter: StreamersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        subscribeToFakeRepo()

        streamersAdapter.setItemClickListener {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }

        //Filter based on search query
        sv_search_streamers.setOnQueryTextListener(mainViewModel.searchCallback)
    }

    private fun setupRecyclerView() = rv_all_streamers.apply {
        adapter = streamersAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToFakeRepo(){
        mainViewModel.streamers.observe(viewLifecycleOwner) {
            streamersAdapter.streamers = it
        }
    }
}