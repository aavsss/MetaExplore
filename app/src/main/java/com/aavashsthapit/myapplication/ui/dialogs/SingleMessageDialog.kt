package com.aavashsthapit.myapplication.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.databinding.SingleMessageDialogBinding
import com.aavashsthapit.myapplication.ui.viewmodels.MainViewModel

class SingleMessageDialog : DialogFragment() {

    companion object {
        const val TAG = "SingleMessageDialog"
    }

    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: SingleMessageDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_message_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding = SingleMessageDialogBinding.bind(view)
        setUpViews()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setUpViews() {
        binding.apply {
            tvTitle.text = mainViewModel.currentStreamer.value?.data?.display_name
            etMessage.setText("Hey, I have a product that can boast your production in ${mainViewModel.currentStreamer.value?.data?.game_name}.")
            btnSubmit.setOnClickListener {
                Toast.makeText(requireContext(), "Sent message", Toast.LENGTH_SHORT).show()
            }
        }
    }

}