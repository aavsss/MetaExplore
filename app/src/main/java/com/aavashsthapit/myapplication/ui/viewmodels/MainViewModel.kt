package com.aavashsthapit.myapplication.ui.viewmodels

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavashsthapit.myapplication.api.TwitchStreamersApi
import com.aavashsthapit.myapplication.data.entity.Streamer
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.other.Constants.TAG
import com.aavashsthapit.myapplication.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Business logic
 * Logic for searchCallback
 * sets currentStreamer for DetailView
 * Performs an HTTPS request to get current valorant streamers. NOTE: Need to use NGROK for now
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    val fakeRepo: FakeRepo,
) : ViewModel() {

    private val _streamers = MutableLiveData<Resource<List<Streamer>>>()
    val streamers : LiveData<Resource<List<Streamer>>> = _streamers

    private val _currentStreamer = MutableLiveData<Resource<Streamer>>()
    val currentStreamer : LiveData<Resource<Streamer>> = _currentStreamer

    lateinit var listener: (() -> Unit)


    fun getSearchCallback(streamers: List<Streamer>) = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                if (newText.isNotEmpty()){
                    val tempList : List<Streamer> = if(streamers.isEmpty()){
                        fakeRepo.streamers.filter {
                            it.display_name.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
                        }
                    }else{
                        streamers.filter {
                            it.display_name.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
                        }
                    }

                    _streamers.postValue(Resource.success(tempList))

                }else{
                    //Show all
                    _streamers.postValue(Resource.success(fakeRepo.streamers))
                }
            }
            return false
        }
    }

    fun setCurrentStreamer(streamer : Streamer){
        _currentStreamer.postValue(Resource.success(streamer))
    }

    fun sendHttpRequest(twitchStreamersApi: TwitchStreamersApi){
        if(fakeRepo.streamers.isEmpty()){
            viewModelScope.launch {
                val response = try {
                    twitchStreamersApi.getTwitchStreamers()
                } catch (e : IOException) {
                    Log.e(TAG, "IOException, you might not have internet connection ${e.localizedMessage}")
                    listener.invoke()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    return@launch
                } catch (e : HttpException) {
                    Log.e(TAG, "HttpException, unexpected response")
                    listener.invoke()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    return@launch
                }

                if(response.isSuccessful && response.body() != null) {
                    Log.v(TAG, "///${response.body()}")
                    response.body()?.data?.let {
                        fakeRepo.streamers = it
                        _streamers.postValue(Resource.success(it))
                    } ?: Resource.error("An unknown error occurred", null)
                }else{
                    listener.invoke()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    Resource.error("An unknown error occurred", null)
                    Log.e(TAG, "Response not successful")
                }
            }
        }
    }

    fun setTestStreamers(streamers : List<Streamer>) {
        _streamers.postValue(Resource.success(streamers))
    }
}