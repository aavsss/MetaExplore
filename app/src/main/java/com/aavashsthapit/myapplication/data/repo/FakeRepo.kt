package com.aavashsthapit.myapplication.data.repo

import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.data.entity.TwitchStreamer

object FakeRepo {

    val streamers = listOf(
            TwitchStreamer("Pokimane", R.drawable.pokimane, true, "Variety"),
            TwitchStreamer("TenZ", R.drawable.tenz, true, "Valorant"),
            TwitchStreamer("Sykkuno", R.drawable.sykkuno, false, "Variety"),
            TwitchStreamer("Hiko", R.drawable.hiko, false, "Valorant"),
            TwitchStreamer("Ploo", R.drawable.ploo, true, "Valorant")
    )

}