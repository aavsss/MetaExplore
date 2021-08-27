package com.aavashsthapit.myapplication.data.entity

data class TwitchResponse(
    val data: List<StreamerViewModel>,
    val pagination: Pagination
)
