package com.example.musicgallery.dagger

import com.example.musicgallery.interfaces.PresenterInterface
import com.example.musicgallery.presenter.Presenter
import dagger.Binds
import dagger.Module

@Module
abstract class PresenterModule {
    @Binds
    abstract fun presenter(presenter: Presenter): PresenterInterface
}