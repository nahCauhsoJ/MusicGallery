package com.example.musicgallery.utils

import com.example.musicgallery.model.Music
import com.example.musicgallery.model.MusicList

fun List<Music>.toMusicList() : MusicList =
    MusicList(
        results = this,
        resultCount = this.count()
    )