package com.aavashsthapit.myapplication.data.repo

import android.util.Log
import com.aavashsthapit.myapplication.api.TwitchStreamersApi
import com.aavashsthapit.myapplication.data.entity.StreamerViewModel
import com.aavashsthapit.myapplication.other.Constants
import com.aavashsthapit.myapplication.other.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fake Repo until backend is connected
 * List of data class TwitchStreamers
 */
@Singleton
class FakeRepo @Inject constructor(
    private val twitchStreamersApi: TwitchStreamersApi,
    private val userCache: UserCache
) : StreamerRepo {

    companion object {
        const val MAX_RETRY_ATTEMPT = 3
    }

    private var attempts = 0

    val testStreamers = listOf(
        StreamerViewModel(display_name = "Pokimane", thumbnail_url = "https://specials-images.forbesimg.com/imageserve/5f5f55887d9eec237a586841/960x0.jpg?fit=scale", is_live = false, game_name = "Variety"),
        StreamerViewModel(display_name = "TenZ", thumbnail_url = "https://liquipedia.net/commons/images/c/c2/TenZ_%40_EPL_S10_Americas.jpg", is_live = true, game_name = "Valorant"),
        StreamerViewModel(display_name = "Sykkuno", thumbnail_url = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/Sykkuno_2020.jpg/220px-Sykkuno_2020.jpg", is_live = false, game_name = "Variety"),
        StreamerViewModel(display_name = "Hiko", thumbnail_url = "data:image/jpeg;base64,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", is_live = false, game_name = "Valorant"),
        StreamerViewModel(display_name = "Ploo", thumbnail_url = "https://yt3.ggpht.com/ytc/AAUvwniu3ghHE6nZ4G2YQr0r8EeZcA6gsDm3yXQ18gON2Q=s900-c-k-c0x00ffffff-no-rj", is_live = true, game_name = "Valorant")
    )
    var streamers = listOf<StreamerViewModel>()

    override fun getAllStreamers(): List<StreamerViewModel> {
        return streamers
    }

    override fun getTestStreamersRe(): List<StreamerViewModel> {
        return testStreamers
    }

    override suspend fun getDataFromBackend(): List<StreamerViewModel> {
        attempts += 1
        val cached = userCache.getCache()
        if (cached != null) {
            return cached
        }
        return try {
            val temp = twitchStreamersApi.getTwitchStreamers()
            return if (temp.isSuccessful && temp.body() != null) {
                userCache.putCache(temp.body()!!.data)
                streamers = temp.body()!!.data
                temp.body()!!.data
            } else {
                testStreamers
            }
        } catch (e: IOException) {
            Resource.error(e.localizedMessage ?: "Error", null)
            Log.e(
                Constants.TAG,
                "IOException, you might not have internet connection ${e.localizedMessage}"
            )
            if (attempts < MAX_RETRY_ATTEMPT) {
                getDataFromBackend()
            } else {
                throw e
            }
        } catch (e: HttpException) {
            Resource.error(e.localizedMessage ?: "Error", null)
            Log.e(Constants.TAG, "HttpException, unexpected response")
            if (attempts < MAX_RETRY_ATTEMPT) {
                getDataFromBackend()
            } else {
                throw e
            }
        }
    }
}
