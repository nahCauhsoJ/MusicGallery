package com.example.musicgallery.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicList(
    @Json(name = "resultCount")
    val resultCount: Int,
    @Json(name = "results")
    val results: List<Music>
)