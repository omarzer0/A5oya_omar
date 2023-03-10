package com.example.myapplication

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView

class AudioHandler (
    private val applicationContext: Context,
) : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private var mediaPlayer: MediaPlayer? = null
    private var nowPlayingAudio: Uri? = null
    private var listener: AudioPlaybackListener? = null
    private var mSeekBarUpdateHandler: Handler? = null
    private var mUpdateSeekBar: Runnable? = null
    private var mSeekBar: SeekBar? = null

    fun initListener(audioPlaybackListener: AudioPlaybackListener) {
        listener = audioPlaybackListener
    }

    // TODO Passing views is bad and will be fix in exo player update
    fun playAudio(audioPath: String, seekBar: SeekBar, textTvToUpdate: TextView) {
        tryNow(tag = "playNewAudio") {
            val audioUri = Uri.parse(audioPath)
            logMe("audioUri == nowPlayingAudio-> ${audioUri == nowPlayingAudio}")
            logMe("mediaPlayer?.isPlaying-> ${mediaPlayer?.isPlaying == true}")
            if (audioUri == nowPlayingAudio) {
                if (mediaPlayer?.isPlaying == true) pauseAudio()
                else resumeAudio()
            } else {
                nowPlayingAudio = audioUri
                playNewAudio(audioUri, seekBar, textTvToUpdate)
            }
        }
    }

    private fun playNewAudio(audioUri: Uri, seekBar: SeekBar, textTvToUpdate: TextView) {
        logMe("playNewAudio uri: $audioUri")
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
            setOnPreparedListener(this@AudioHandler)
            setOnCompletionListener(this@AudioHandler)
            setDataSource(applicationContext, audioUri)
            prepare()
            setVolume(1.0f, 1.0f)
            prepareAndListenToPositionSeekBarChanges(
                seekBar,
                mediaPlayer?.duration ?: return@apply,
                textTvToUpdate
            )
            updateSeekBar(seekBar, textTvToUpdate)
            start()
            logMe("${mediaPlayer?.duration}", "duration")

            listener?.onStart()
        } ?: logMe("mp null")
    }

    private fun prepareAndListenToPositionSeekBarChanges(
        seekBar: SeekBar,
        totalTime: Int,
        textTvToUpdate: TextView
    ) {
        seekBar.apply {
            max = totalTime
            progress = 0
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                if (p2) {
                    seekBar.progress = progress
                    logMe("progress: $progress", "progress")
                    mediaPlayer?.seekTo(progress)
                    textTvToUpdate.text = createTimeLabel(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }

    private fun updateSeekBar(
        seekBar: SeekBar, textTvToUpdate: TextView
    ) {
        mSeekBar = seekBar
        mSeekBarUpdateHandler = Handler(Looper.getMainLooper())
        mUpdateSeekBar = Runnable {
            val currentPosition = mediaPlayer?.currentPosition ?: return@Runnable
            textTvToUpdate.text = createTimeLabel(currentPosition)
            mSeekBar?.progress = currentPosition
            mSeekBarUpdateHandler?.postDelayed(mUpdateSeekBar ?: return@Runnable, 50)
                ?: return@Runnable
        }
        mSeekBarUpdateHandler?.postDelayed(mUpdateSeekBar!!, 0)
    }

    fun createTimeLabel(time: Int): String {
        val min = time / 1000 / 60
        val sec = time / 1000 % 60
        logMe("time: min:$min sec:$sec", "createTimeLabel")
        return "$min:$sec"
    }

    private fun resumeAudio() {
        logMe("resumeAudio $mediaPlayer $listener")
        mediaPlayer?.apply {
            start()
            listener?.onResume()
        }
    }

    private fun pauseAudio() {
        logMe("pauseAudio")
        mediaPlayer?.apply {
            pause()
            listener?.onPause()
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        logMe("onCompletion $mp ${mp?.isPlaying}")
        nowPlayingAudio = null
        listener?.onCompletion()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.apply {
            release()
            mediaPlayer = null
            mSeekBar?.progress = 0
            mUpdateSeekBar?.let { mSeekBarUpdateHandler?.removeCallbacks(it) }
        }
    }

    fun clearAll() {
        mediaPlayer?.stop()
        releaseMediaPlayer()
        listener = null
        mediaPlayer = null
        nowPlayingAudio = null
        mSeekBarUpdateHandler?.removeCallbacks(mUpdateSeekBar!!)
    }


    override fun onPrepared(p0: MediaPlayer?) {
        listener?.onStart()
    }
}

interface AudioPlaybackListener {
    fun onCompletion()
    //    fun onStop()
    fun onPause()
    fun onStart()
    fun onResume()
}

private fun logMe(msg: String, tag: String = "TAG") {
    val showLog = false
    if (showLog) Log.e(tag, msg)
}


fun tryNow(
    tag: String = "",
    error: ((Exception) -> Unit)? = null,
    action: () -> Unit
) {
    try {
        action()
    } catch (e: Exception) {
        logMe("error $tag ${e.localizedMessage ?: "Unknown"}", "tryNow")
    }
}