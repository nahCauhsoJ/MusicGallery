package com.example.musicgallery.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MusicDAO {
    @Query("SELECT * FROM music WHERE apiSearchTerm == :apiSearchTerm")
    fun getMusicList(apiSearchTerm: String): Single<List<MusicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(vararg card: MusicEntity): Completable
}