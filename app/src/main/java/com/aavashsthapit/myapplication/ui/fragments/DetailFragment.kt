package com.aavashsthapit.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.databinding.FragmentDetailBinding
import com.aavashsthapit.myapplication.ui.dialogs.SingleMessageDialog
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupViews()
    }

    private fun setupViews() {
        mainViewModel.currentStreamer.observe(viewLifecycleOwner) {
            binding.apply {
                viewmodel = mainViewModel
                fabSendMessage.setOnClickListener {
                    activity?.supportFragmentManager?.let { supportFragmentManager ->
                        SingleMessageDialog().show(
                            supportFragmentManager,
                            SingleMessageDialog.TAG
                        )
                    }
                }
            }
        }
    }
}

