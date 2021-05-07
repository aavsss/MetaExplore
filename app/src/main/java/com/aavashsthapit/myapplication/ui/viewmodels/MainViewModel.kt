package com.aavashsthapit.myapplication.ui.viewmodels

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavashsthapit.myapplication.api.TwitchStreamersApi
import com.aavashsthapit.myapplication.data.entity.TwitchStreamer
import com.aavashsthapit.myapplication.data.repo.FakeRepo
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
 */
const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    fakeRepo: FakeRepo,
) : ViewModel() {
    private val _streamers = MutableLiveData<List<TwitchStreamer>>()
    val streamers : LiveData<List<TwitchStreamer>> = _streamers

    private val _currentStreamer = MutableLiveData<TwitchStreamer>()
    val currentStreamer : LiveData<TwitchStreamer> = _currentStreamer

    init {
        _streamers.postValue(fakeRepo.streamers)
    }

    val searchCallback = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                if (newText.isNotEmpty()){
                    val tempList = fakeRepo.streamers.filter {
                        it.name.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
                    }
                    _streamers.postValue(tempList)
                }else{
                    //Show all
                    _streamers.postValue(fakeRepo.streamers)
                }
            }
            return false
        }
    }

    fun setCurrentStreamer(streamer : TwitchStreamer){
        _currentStreamer.postValue(streamer)
    }

    fun sendHttpRequest(twitchStreamersApi: TwitchStreamersApi){
        Log.v("asda", "/// $twitchStreamersApi")
        viewModelScope.launch {
            val response = try {
                twitchStreamersApi.getTwitchStreamers()
            } catch (e : IOException) {
                Log.e(TAG, "IOException, you might not have internet connection ${e.localizedMessage}")
                return@launch
            } catch (e : HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                Log.v("asda", "///${response.body()}")
            }else{
                Log.e(TAG, "Response not successful")
            }
        }
    }
}