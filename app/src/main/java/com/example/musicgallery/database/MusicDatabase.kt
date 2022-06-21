package com.example.musicgallery.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MusicEntity::class],
    version = 1
)
abstract class MusicDatabase: RoomDatabase() {
    abstract fun getMusicDAO(): MusicDAO
}