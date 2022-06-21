package com.example.musicgallery.dagger

import com.example.musicgallery.database.LocalRepository
import com.example.musicgallery.interfaces.LocalRepositoryInterface
import com.example.musicgallery.interfaces.RepositoryInterface
import com.example.musicgallery.network.Repository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun localRepository(
        localRepository: LocalRepository): LocalRepositoryInterface

    @Binds
    abstract fun repository(
        repository: Repository): RepositoryInterface
}