package com.aavashsthapit.myapplication.data.entity

data class Streamer(
    val broadcaster_language : String,
    val broadcaster_login : String,
    val display_nam : String,
    val game_id : String,
    val game_name : String,
    val id : String,
    val is_live : Boolean,
    val tag_ids : List<String>,
    val thumbnail_url : String,
    val title : String,
    val started_at : String
)
