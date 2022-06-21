package com.example.musicgallery.interfaces

import android.widget.SeekBar

interface PresenterInterface {
    fun initializePresenter(viewInterface: ViewInterface)
    fun registerForNetworkState()
    fun getData(music_type: String)
    fun destroyPresenter()

    fun playMusic(url: String, seekBar: SeekBar?): Boolean
    fun pauseMusic()
    fun endMusic()
    fun isMusicPrepared(): Boolean
    fun setMusicProgress(progress: Int)
}