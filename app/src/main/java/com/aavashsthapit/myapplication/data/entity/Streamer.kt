package com.aavashsthapit.myapplication.data.entity

data class Streamer(
    val broadcaster_language: String = "",
    val broadcaster_login: String = "",
    val display_name: String = "",
    val game_id: String = "",
    val game_name: String = "",
    val id: String = "",
    val is_live: Boolean = false,
    val tag_ids: List<String> = listOf(),
    val thumbnail_url: String = "",
    val title: String = "",
    val started_at: String = "",
    var expanded: Boolean = false
)
