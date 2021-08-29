package com.aavashsthapit.myapplication.ui.viewmodels

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavashsthapit.myapplication.api.TwitchStreamersApi
import com.aavashsthapit.myapplication.data.entity.StreamerViewModel
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.other.Constants.TAG
import com.aavashsthapit.myapplication.other.Event
import com.aavashsthapit.myapplication.other.Resource
import com.aavashsthapit.myapplication.ui.fragments.homeFragment.FilterStreamers
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
    val fakeRepo: FakeRepo
) : ViewModel() {

    @Inject
    lateinit var filterStreamers: FilterStreamers

    private val _streamers = MutableLiveData<Resource<List<StreamerViewModel>>>()
    val streamers: LiveData<Resource<List<StreamerViewModel>>> = _streamers

    private val _currentStreamer = MutableLiveData<Resource<StreamerViewModel>>()
    val currentStreamerViewModel: LiveData<Resource<StreamerViewModel>> = _currentStreamer

    private val _showSendMessageDialog = MutableLiveData<Event<Resource<Unit>>>()
    val showSendMessageDialog: LiveData<Event<Resource<Unit>>> = _showSendMessageDialog

    private val _progressBarListener = MutableLiveData<Resource<Unit>>()
    val progressBarListener: LiveData<Resource<Unit>> = _progressBarListener

    private val _getSearchCallback = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            _streamers.postValue(Resource.success(filterStreamers.searchStreamers(newText)))
            return false
        }
    }

    val getSearchCallback: SearchView.OnQueryTextListener = _getSearchCallback

    fun setCurrentStreamer(streamerViewModel: StreamerViewModel) {
        _currentStreamer.postValue(Resource.success(streamerViewModel))
    }

    fun sendHttpRequest(twitchStreamersApi: TwitchStreamersApi) {
        if (fakeRepo.streamers.isEmpty()) {
            viewModelScope.launch {
                val response = try {
                    twitchStreamersApi.getTwitchStreamers()
                } catch (e: IOException) {
                    Log.e(TAG, "IOException, you might not have internet connection ${e.localizedMessage}")
                    hideProgressBar()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    return@launch
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException, unexpected response")
                    hideProgressBar()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {
                    Log.v(TAG, "///${response.body()}")
                    response.body()?.data?.let {
                        fakeRepo.streamers = it
                        _streamers.postValue(Resource.success(it))
                    } ?: Resource.error("An unknown error occurred", null)
                } else {
                    hideProgressBar()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    Resource.error("An unknown error occurred", null)
                    Log.e(TAG, "Response not successful")
                }
            }
        }
    }

    fun setTestStreamers(streamerViewModels: List<StreamerViewModel>) {
        _streamers.postValue(Resource.success(streamerViewModels))
    }

    fun showDialog() {
        _showSendMessageDialog.postValue(Event(Resource.success(Unit)))
    }

    fun hideProgressBar() {
        _progressBarListener.postValue(Resource.success(Unit))
    }
}
