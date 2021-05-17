package com.aavashsthapit.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.databinding.ActivityMainBinding
import com.aavashsthapit.myapplication.ui.fragments.StreamersFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Setting up Fragments
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentFactory: StreamersFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}