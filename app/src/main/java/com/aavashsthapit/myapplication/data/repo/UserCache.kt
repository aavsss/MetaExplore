package com.aavashsthapit.myapplication.data.repo

import com.aavashsthapit.myapplication.data.entity.StreamerViewModel

interface UserCache {
    fun getCache(): List<StreamerViewModel>?
    fun putCache(list: List<StreamerViewModel>)
}