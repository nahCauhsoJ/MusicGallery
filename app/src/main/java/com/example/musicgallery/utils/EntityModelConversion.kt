package com.example.musicgallery.utils

import com.example.musicgallery.database.MusicEntity
import com.example.musicgallery.model.Music

fun List<MusicEntity>.MusicEntityToMusicModel(): List<Music> =
    this.map {
        Music(
            artistId = it.artistId,
            artistName = it.artistName,
            artistViewUrl = it.artistViewUrl,
            artworkUrl100 = it.artworkUrl100,
            artworkUrl30 = it.artworkUrl30,
            artworkUrl60 = it.artworkUrl60,
            collectionCensoredName = it.collectionCensoredName,
            collectionExplicitness = it.collectionExplicitness,
            collectionId = it.collectionId,
            collectionName = it.collectionName,
            collectionPrice = it.collectionPrice,
            collectionViewUrl = it.collectionViewUrl,
            country = it.country,
            currency = it.currency,
            discCount = it.discCount,
            discNumber = it.discNumber,
            isStreamable = it.isStreamable,
            kind = it.kind,
            previewUrl = it.previewUrl,
            primaryGenreName = it.primaryGenreName,
            releaseDate = it.releaseDate,
            trackCensoredName = it.trackCensoredName,
            trackCount = it.trackCount,
            trackExplicitness = it.trackExplicitness,
            trackId = it.trackId,
            trackName = it.trackName,
            trackNumber = it.trackNumber,
            trackPrice = it.trackPrice,
            trackTimeMillis = it.trackTimeMillis,
            trackViewUrl = it.trackViewUrl,
            wrapperType = it.wrapperType
        )
    }

fun List<Music>.MusicModelToMusicEntity(
    apiSearchTerm: String): List<MusicEntity> =
    this.map {
        MusicEntity(
            apiSearchTerm = apiSearchTerm,
            artistId = it.artistId,
            artistName = it.artistName,
            artistViewUrl = it.artistViewUrl,
            artworkUrl100 = it.artworkUrl100,
            artworkUrl30 = it.artworkUrl30,
            artworkUrl60 = it.artworkUrl60,
            collectionCensoredName = it.collectionCensoredName,
            collectionExplicitness = it.collectionExplicitness,
            collectionId = it.collectionId,
            collectionName = it.collectionName,
            collectionPrice = it.collectionPrice,
            collectionViewUrl = it.collectionViewUrl,
            country = it.country,
            currency = it.currency,
            discCount = it.discCount,
            discNumber = it.discNumber,
            isStreamable = it.isStreamable,
            kind = it.kind,
            previewUrl = it.previewUrl,
            primaryGenreName = it.primaryGenreName,
            releaseDate = it.releaseDate,
            trackCensoredName = it.trackCensoredName,
            trackCount = it.trackCount,
            trackExplicitness = it.trackExplicitness,
            trackId = it.trackId,
            trackName = it.trackName,
            trackNumber = it.trackNumber,
            trackPrice = it.trackPrice,
            trackTimeMillis = it.trackTimeMillis,
            trackViewUrl = it.trackViewUrl,
            wrapperType = it.wrapperType
        )
    }