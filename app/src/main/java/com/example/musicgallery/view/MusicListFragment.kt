package com.example.musicgallery.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.musicgallery.R
import com.example.musicgallery.databinding.FragmentMusicListBinding
import com.example.musicgallery.interfaces.PresenterInterface
import com.example.musicgallery.interfaces.ViewInterface
import com.example.musicgallery.model.MusicList
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MusicListFragment : Fragment(), ViewInterface {

    private val binding by lazy { FragmentMusicListBinding.inflate(layoutInflater) }
    private val musicAdapter by lazy { MusicAdapter(
        expandCardFunc = {},
        collapseCardFunc = { presenter.endMusic() },
        playMusicFunc = { url: String, seekBar: SeekBar -> presenter.playMusic(url, seekBar) },
        pauseMusicFunc = {presenter.pauseMusic()},
        gotoDetailsFunc = { url: String ->
            view?.apply {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if (intent.resolveActivity(this.context.packageManager) != null) {
                    this.context.startActivity(intent)
                } else {
                    Snackbar.make(this, "Unable to visit website", Snackbar.LENGTH_SHORT).show()
                }
            }
        },
        setMusicProgress = { progress: Int -> presenter.setMusicProgress(progress) }
    ) }

    @Inject
    lateinit var presenter: PresenterInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MusicApp.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.musicListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = musicAdapter
        }

        presenter.initializePresenter(this)
        presenter.registerForNetworkState()
        arguments?.getString("music_type")?.let {
            presenter.getData(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroyPresenter()
    }

    override fun loadingState() { binding.musicDataLoading.visibility = View.VISIBLE }
    override fun connectionChecked() {}
    override fun onLoad(music: MusicList) {
        binding.musicDataError.visibility = View.INVISIBLE
        musicAdapter.refreshMusicList(music.results)
        if (music.results.isEmpty()) {
            binding.musicDataError.text = getString(R.string.no_music_error)
            binding.musicDataError.visibility = View.VISIBLE
        }
        binding.musicDataLoading.visibility = View.INVISIBLE
    }
    override fun onError(error: Throwable) {
        binding.musicDataError.text = getString(R.string.unknown_error)
        binding.musicDataError.visibility = View.VISIBLE
        binding.musicDataLoading.visibility = View.INVISIBLE
    }
    override fun makeToast(text: String, long: Boolean) {
        Toast.makeText(
            requireContext(),
            text,
            if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }

    override fun processOffline() {
        makeToast(getString(R.string.no_connection_error))
    }
}