package com.example.musicgallery.dagger

import com.example.musicgallery.databinding.FragmentMusicListBinding
import com.example.musicgallery.view.MainActivity
import com.example.musicgallery.view.MusicListFragment
import dagger.Component
import javax.inject.Singleton


@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        PresenterModule::class,
        RepositoryModule::class
    ]
)
interface MusicComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(musicListFragment: MusicListFragment)
}