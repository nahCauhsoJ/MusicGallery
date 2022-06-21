package com.example.musicgallery.interfaces

import com.example.musicgallery.model.MusicList
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

interface RepositoryInterface {
    val networkState: BehaviorSubject<Boolean>
    fun registerNetworkAvailability()
    fun unregisterNetworkAvailability()
    fun getData(musicType: String): Single<MusicList>
}