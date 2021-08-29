package com.aavashsthapit.myapplication.ui.fragments.homeFragment

import com.aavashsthapit.myapplication.data.entity.StreamerViewModel

interface FilterStreamers {
    fun searchStreamers(query: String?): List<StreamerViewModel>
}
