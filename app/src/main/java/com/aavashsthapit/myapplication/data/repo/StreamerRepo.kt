package com.aavashsthapit.myapplication.data.repo

import com.aavashsthapit.myapplication.data.entity.StreamerViewModel

interface StreamerRepo {
    fun getSelectedStreamer(): StreamerViewModel // subject to remove
    fun getAllStreamers(): List<StreamerViewModel>
}