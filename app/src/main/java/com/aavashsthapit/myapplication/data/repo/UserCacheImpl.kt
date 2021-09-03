package com.aavashsthapit.myapplication.data.repo

import com.aavashsthapit.myapplication.data.entity.StreamerViewModel
import javax.inject.Inject

class UserCacheImpl @Inject constructor() : UserCache {

    private var streamers: List<StreamerViewModel>? = null

    override fun getCache(): List<StreamerViewModel>? {
        return streamers
    }

    override fun putCache(list: List<StreamerViewModel>) {
        streamers = list
    }
}
