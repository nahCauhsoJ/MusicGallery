package com.example.musicgallery.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicgallery.databinding.FragmentMusicCardBinding
import com.example.musicgallery.model.Music

class MusicAdapter(
    private val musicList: MutableList<Music> = mutableListOf(),
    private val expandCardFunc: () -> Unit,
    private val collapseCardFunc: () -> Unit,
    private val playMusicFunc: (String, SeekBar) -> Unit,
    private val pauseMusicFunc: () -> Unit,
    private val gotoDetailsFunc: (String) -> Unit,
    private val setMusicProgress: (Int) -> Unit
) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    fun refreshMusicList(music: List<Music>) {
        musicList.clear()
        musicList.addAll(music.sortedBy { x -> x.trackName })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            FragmentMusicCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    inner class ViewHolder(
        private val binding: FragmentMusicCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private var imageLoaded: Boolean = false

            fun bind(
                music: Music
            ) {
                imageLoaded = false // For refreshing

                binding.musicTrackName.text = music.trackName
                binding.musicArtistName.text = music.artistName
                binding.musicTrackPrice.text = "${music.trackPrice}${music.currency}"

                binding.musicSummaryCard.setOnClickListener {
                    if (binding.musicPreviewCard.visibility == View.VISIBLE) {
                        binding.musicPreviewCard.visibility = View.GONE
                        collapseCardFunc()
                    } else {
                        binding.musicPreviewCard.visibility = View.VISIBLE
                        expandCardFunc()
                        if (!imageLoaded)
                        {
                            Glide.with(binding.root)
                                .load(music.artworkUrl60)
                                .into(binding.musicArtwork)
                            imageLoaded = true
                        }
                    }
                }

                binding.musicPreviewBtnPlay.setOnClickListener {
                    playMusicFunc(music.previewUrl, binding.musicPreviewProgress)
                }
                binding.musicPreviewBtnPause.setOnClickListener { pauseMusicFunc() }
                binding.musicDetailsBtn.setOnClickListener { gotoDetailsFunc(music.trackViewUrl) }

                binding.musicPreviewProgress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if (fromUser) setMusicProgress(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
            }
    }
}