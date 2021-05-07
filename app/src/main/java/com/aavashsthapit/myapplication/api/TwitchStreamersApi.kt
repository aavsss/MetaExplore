package com.aavashsthapit.myapplication.api

import com.aavashsthapit.myapplication.data.entity.TwitchResponse
import retrofit2.Response
import retrofit2.http.GET

interface TwitchStreamersApi {

    @GET("/twitchStreamers")
    suspend fun getTwitchStreamers() : Response<TwitchResponse>
}