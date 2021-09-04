package com.aavashsthapit.myapplication.data.repo

import com.aavashsthapit.myapplication.data.entity.StreamerViewModel

interface StreamerRepo {
    fun getAllStreamers(): List<StreamerViewModel>
    fun getTestStreamersRe(): List<StreamerViewModel> // subject to remove
    suspend fun getDataFromBackend(): List<StreamerViewModel>
}