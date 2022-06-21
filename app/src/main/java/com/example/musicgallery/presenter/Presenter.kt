package com.example.musicgallery.presenter

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import com.example.musicgallery.database.LocalRepository
import com.example.musicgallery.interfaces.PresenterInterface
import com.example.musicgallery.interfaces.ViewInterface
import com.example.musicgallery.model.MusicList
import com.example.musicgallery.network.Repository
import com.example.musicgallery.utils.MusicEntityToMusicModel
import com.example.musicgallery.utils.MusicModelToMusicEntity
import com.example.musicgallery.utils.toMusicList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Presenter @Inject constructor(
    private val repository: Repository,
    private val localRepository: LocalRepository,
    private val compositeDisposable: CompositeDisposable,
    private val mediaPlayer: MediaPlayer
) : PresenterInterface {
    private var viewInterface: ViewInterface? = null
    private var isPreviewPreparing: Boolean = false // Button spam protection
    private var isPreviewPrepared: Boolean = false

    private var currentPreviewUrl: String = ""
    private var currentPreviewSeekBar: SeekBar? = null
    private val previewSeekBarHandler: Handler = Handler(Looper.getMainLooper())
    private var previewSeekBarRunnable: Runnable? = null

    override fun initializePresenter(viewInterface: ViewInterface) {
        this.viewInterface = viewInterface
    }

    override fun registerForNetworkState() {
        repository.registerNetworkAvailability()
    }

    override fun getData(music_type: String) {
        viewInterface?.loadingState()
        repository.networkState
            .subscribe{ netState ->
                if (netState) {
                    repository.getData(music_type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                viewInterface?.onLoad(it)
                                insertData(music_type,it) },
                            {
                                getDataOffline(music_type) })
                        .also { compositeDisposable.add(it) }
                } else {
                    getDataOffline(music_type)
                }
            }.also { compositeDisposable.add(it) }
    }

    override fun destroyPresenter() {
        viewInterface = null
        repository.unregisterNetworkAvailability()
        compositeDisposable.dispose()
        endMusic()
    }

    override fun playMusic(url: String, seekBar: SeekBar?): Boolean = mediaPlayer.apply {
        if (isPreviewPreparing) return true
        // This prepares for the case when users click the play button of
        //      another music's play button.
        if (url.isNotEmpty() && url != currentPreviewUrl) {
            endMusic()
            currentPreviewUrl = url
        }

        if (!isPreviewPrepared) {
            isPreviewPreparing = true
            prepareMusic(url) {
                if (seekBar != null) syncSeekBarWithMediaPlayer(seekBar)
                this.start()
                isPreviewPreparing = false
                isPreviewPrepared = true
            }.run { return@run }
        } else this.start()
    }.run {return true}
    override fun pauseMusic() { if (isPreviewPrepared) mediaPlayer.pause() }
    override fun endMusic() = mediaPlayer.reset().also {
        isPreviewPrepared = false
        currentPreviewUrl = ""
        currentPreviewSeekBar = null
        previewSeekBarRunnable?.let { it1 -> previewSeekBarHandler.removeCallbacks(it1) }
        previewSeekBarRunnable = null
    }

    override fun isMusicPrepared(): Boolean = isPreviewPrepared
    override fun setMusicProgress(progress: Int) = mediaPlayer.seekTo(progress * 1000)

    private fun getDataOffline(music_type: String) {
        localRepository.getMusicList(music_type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewInterface?.onLoad(it.MusicEntityToMusicModel().toMusicList())
                    viewInterface?.processOffline()
                },
                { viewInterface?.onError(it) })
            .also { compositeDisposable.add(it) }
    }

    private fun insertData(music_type: String, musicList: MusicList) {
        localRepository.insertMusic(
            musicList.results.MusicModelToMusicEntity(music_type))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    private fun prepareMusic(url: String, preparedFunction: () -> Unit): Boolean =
        mediaPlayer.apply {
            try {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { preparedFunction() }
            } catch (e: Exception) { return false }
        }.run { return true }

    private fun syncSeekBarWithMediaPlayer(seekBar: SeekBar) {
        seekBar.max = mediaPlayer.duration / 1000
        seekBar.progress = mediaPlayer.currentPosition
        currentPreviewSeekBar = seekBar

        previewSeekBarRunnable = object: Runnable {
            override fun run() {
                seekBar.progress = mediaPlayer.currentPosition / 1000
                previewSeekBarHandler.postDelayed(this, 1000)
            }
        }
        previewSeekBarHandler.post(previewSeekBarRunnable as Runnable)
    }
}