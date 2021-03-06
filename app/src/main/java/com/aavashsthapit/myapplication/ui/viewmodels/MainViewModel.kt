package com.aavashsthapit.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavashsthapit.myapplication.data.entity.StreamerViewModel
import com.aavashsthapit.myapplication.data.repo.FakeRepo
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

    val vs: (String?) -> Unit = { s: String? ->
        _streamers.postValue(Resource.success(filterStreamers.searchStreamers(s)))
    }

    fun setCurrentStreamer(streamerViewModel: StreamerViewModel) {
        _currentStreamer.postValue(Resource.success(streamerViewModel))
    }

    fun sendHttpRequest() {
        if (fakeRepo.streamers.isEmpty()) {
            viewModelScope.launch {
                val response = try {
                    fakeRepo.getDataFromBackend()
                } catch (e: IOException) {
                    hideProgressBar()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers)) // show a different view here
                    return@launch
                } catch (e: HttpException) {
                    hideProgressBar()
                    _streamers.postValue(Resource.success(fakeRepo.testStreamers))
                    return@launch
                }

                _streamers.postValue(Resource.success(response))
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

    fun settleCurrentStreamerTo(streamer: StreamerViewModel) {
        _currentStreamer.value = Resource.success(streamer)
    }

    fun expandFieldOf(streamer: StreamerViewModel) {
        streamer.expanded = !streamer.expanded
    }
}
