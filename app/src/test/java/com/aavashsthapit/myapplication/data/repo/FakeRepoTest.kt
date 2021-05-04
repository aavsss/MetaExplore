package com.aavashsthapit.myapplication.data.repo

import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.data.entity.TwitchStreamer
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class FakeRepoTest {

    private lateinit var listOfStreamers : List<TwitchStreamer>

    @Before
    fun setup(){
        listOfStreamers = FakeRepo.streamers
    }

    @After
    fun teardown(){
        listOfStreamers = listOf()
    }

    @Test
    fun `5 streamers in list`(){
        assertThat(listOfStreamers).contains(TwitchStreamer("Pokimane", R.drawable.pokimane, true, "Variety"))
        assertThat(listOfStreamers).contains(TwitchStreamer("TenZ", R.drawable.tenz, true, "Valorant"))
        assertThat(listOfStreamers).contains(TwitchStreamer("Sykkuno", R.drawable.sykkuno, false, "Variety"))
        assertThat(listOfStreamers).contains(TwitchStreamer("Hiko", R.drawable.hiko, false, "Valorant"))
        assertThat(listOfStreamers).contains(TwitchStreamer("Ploo", R.drawable.ploo, true, "Valorant"))
    }
}