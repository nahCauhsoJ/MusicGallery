package com.example.musicgallery.dagger

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.musicgallery.database.MusicDAO
import com.example.musicgallery.database.MusicDatabase
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
    @Provides
    fun context(): Context = application.applicationContext

    @Provides
    fun connectivityManager(context: Context): ConnectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager

    @Provides
    fun musicDatabase(context: Context): MusicDatabase =
        Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            "music_db"
        ).build()

    @Provides
    fun musicDao(musicDatabase: MusicDatabase): MusicDAO =
        musicDatabase.getMusicDAO()

    @Provides
    fun mediaPlayer(): MediaPlayer =
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
}