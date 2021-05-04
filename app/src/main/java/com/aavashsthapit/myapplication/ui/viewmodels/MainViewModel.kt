package com.aavashsthapit.myapplication.ui.viewmodels

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aavashsthapit.myapplication.data.entity.TwitchStreamer
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

/**
 * Business logic
 * Logic for searchCallback
 * sets currentStreamer for DetailView
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    fakeRepo: FakeRepo
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
}