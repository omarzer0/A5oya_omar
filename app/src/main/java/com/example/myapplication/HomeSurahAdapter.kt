package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemHomePlayShiekhBinding

class HomeSurahAdapter(
    private val audioHandler: AudioHandler , val onPlayAudio: (PlayShiekh) -> Unit,
) : ListAdapter<PlayShiekh, HomeSurahAdapter.HomeSurahViewHolder>(DiffCallback()),
    AudioPlaybackListener {

    private lateinit var binding: ItemHomePlayShiekhBinding
    private var index = -1

    class DiffCallback : DiffUtil.ItemCallback<PlayShiekh>() {
        override fun areItemsTheSame(oldItem: PlayShiekh, newItem: PlayShiekh): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayShiekh, newItem: PlayShiekh): Boolean {
            return oldItem == newItem
        }
    }

    inner class HomeSurahViewHolder(val view: ItemHomePlayShiekhBinding) :
        RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSurahViewHolder {
        binding = ItemHomePlayShiekhBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeSurahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeSurahViewHolder, position: Int) {
        val surah = getItem(position)
        holder.view.surah = surah

//        holder.view.playPauseBtn.setOnClickListener {
//            audioHandler.playAudio(surah.pathOrUrl,holder.view.seekBar,holder.view.timerEnd)
//            notifyItemChanged(index)
//            index = position
//            notifyItemChanged(index)
//        }
//
//        if (index == position) {
//
//        } else {
//
//        }

        val drawableId = if (surah.isPlaying) {
            R.drawable.baseline_pause_presentation_24
        } else {
            R.drawable.baseline_play_circle_outline_24
        }

        holder.view.playPauseBtn.setImageDrawable(
            AppCompatResources.getDrawable(
                binding.root.context,
                drawableId
            )
        )

        holder.view.playPauseBtn.setOnClickListener {
            audioHandler.playAudio(surah.pathOrUrl,holder.view.seekBar,holder.view.timerStart)
            onPlayAudio(surah)
            notifyDataSetChanged()
        }


    }

    override fun onCompletion() {

    }

    override fun onPause() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

}