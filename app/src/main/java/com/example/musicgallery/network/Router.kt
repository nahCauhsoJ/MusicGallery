package com.example.musicgallery.network

import com.example.musicgallery.model.MusicList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Router {

    @GET(BASE_PATH)
    fun getMusic(
        //@Url url: String
        @Query("term") musicType: String,
        @Query("entity") queryEntity: String = "song",
        @Query("limit") queryLimit: Int = 50,
    ): Single<MusicList>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
        private const val BASE_PATH = "search"
    }
}