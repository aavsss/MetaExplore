package com.aavashsthapit.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.databinding.FragmentDetailBinding
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Uses ViewBinding
 * Uses mainViewModel
 * Sets up detail views
 */
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    lateinit var glide: RequestManager

    lateinit var mainViewModel: MainViewModel

    lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupViews()
    }

    private fun setupViews(){
        mainViewModel.currentStreamer.observe(viewLifecycleOwner) {
            binding.apply {
                tvName.text = it.data?.display_name ?: "TenZ"
                tvCategory.text = it.data?.game_name ?: "Valorant"
                glide.load(it.data?.thumbnail_url).into(ivStreamerImg)
            }
        }
    }


}