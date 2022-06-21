package com.example.musicgallery.interfaces

import com.example.musicgallery.database.MusicEntity
import com.example.musicgallery.model.MusicList
import io.reactivex.Completable
import io.reactivex.Single

interface LocalRepositoryInterface {
    fun getMusicList(apiSearchTerm: String): Single<List<MusicEntity>>
    fun insertMusic(musicEntityList: List<MusicEntity>): Completable
}