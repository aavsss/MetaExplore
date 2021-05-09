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
const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    val fakeRepo: FakeRepo,
) : ViewModel() {
    private val _streamers = MutableLiveData<List<Streamer>>()
    val streamers : LiveData<List<Streamer>> = _streamers

    private val _currentStreamer = MutableLiveData<Streamer>()
    val currentStreamer : LiveData<Streamer> = _currentStreamer

    lateinit var listener: (() -> Unit)

    val searchCallback = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                if (newText.isNotEmpty()){
                    val tempList = fakeRepo.streamers.filter {
                        it.display_name.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))
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

    fun setCurrentStreamer(streamer : Streamer){
        _currentStreamer.postValue(streamer)
    }

    fun sendHttpRequest(twitchStreamersApi: TwitchStreamersApi){
        viewModelScope.launch {
            val response = try {
                twitchStreamersApi.getTwitchStreamers()
            } catch (e : IOException) {
                Log.e(TAG, "IOException, you might not have internet connection ${e.localizedMessage}")
                listener.invoke()
                _streamers.postValue(fakeRepo.testStreamers)
                return@launch
            } catch (e : HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                listener.invoke()
                _streamers.postValue(fakeRepo.testStreamers)
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                Log.v(TAG, "///${response.body()}")
                fakeRepo.streamers = response.body()!!.data
                _streamers.postValue(fakeRepo.streamers)
            }else{
                listener.invoke()
                _streamers.postValue(fakeRepo.testStreamers)
                Log.e(TAG, "Response not successful")
            }
        }
    }
}