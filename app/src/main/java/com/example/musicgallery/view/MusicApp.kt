package com.example.musicgallery.view

import android.app.Application
import com.example.musicgallery.dagger.ApplicationModule
import com.example.musicgallery.dagger.MusicComponent
import com.example.musicgallery.dagger.DaggerMusicComponent

class MusicApp: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerMusicComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object { lateinit var component: MusicComponent }
}