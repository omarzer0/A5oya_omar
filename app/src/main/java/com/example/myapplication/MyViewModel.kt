package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private val _list: MutableLiveData<List<PlayShiekh>> = MutableLiveData()
    val list: LiveData<List<PlayShiekh>> get() = _list

    init {
        _list.value = mutableListOf(
            PlayShiekh(
                0,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://cdn.pixabay.com/download/audo/2022/03/09/audio_f81102bd9e.mp3?filename=deep-house-pluck-25180.mp3",
                "",
                "",
                false
            ),
            PlayShiekh(
                1,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3?_=1",
                "",
                "",
                false
            ),
            PlayShiekh(
                2,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3",
                "",
                "",
                false
            ),
            PlayShiekh(
                3,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://cdn.pixabay.com/download/audio/2022/03/24/audio_d79a27e3bf.mp3?filename=cadillac-escalade-engine-mp3-103267.mp3",
                "",
                "",
                false
            ),
            PlayShiekh(
                4,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://elftaha.alexon.work/dashboardAssets/sounds/fatha.mp3",
                "",
                "",
                false
            ),
            PlayShiekh(
                5,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3",
                "",
                "",
                false
            ),
            PlayShiekh(
                6,
                "الشيخ/ عبد الباسط عبد الصمد",
                "https://i1.sndcdn.com/artworks-0fL28imBWDSzhKyK-j0EhSA-t500x500.jpg",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3",
                "",
                "",
                false
            ),
        )
    }

    fun updateList(playShiekh: PlayShiekh) {
        val newList = _list.value?.toMutableList() ?: mutableListOf()
        newList.find { it.id == playShiekh.id }?.isPlaying = true

        newList.forEach {
            Log.e("",it.toString())
        }
        _list.value = newList

    }
}