package com.example.musicgallery.database

import android.util.Log
import com.example.musicgallery.interfaces.LocalRepositoryInterface
import com.example.musicgallery.model.MusicList
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val musicDAO: MusicDAO
): LocalRepositoryInterface {
    override fun getMusicList(
        apiSearchTerm: String): Single<List<MusicEntity>> {
        Log.d("BBBBBBB",apiSearchTerm)
        return musicDAO.getMusicList(apiSearchTerm)
    }

    override fun insertMusic(
        musicEntityList: List<MusicEntity>): Completable {
        Log.d("BBBBBBB",musicEntityList.count().toString())
        return musicDAO.insertMusic(*musicEntityList.toTypedArray())
    }
}
