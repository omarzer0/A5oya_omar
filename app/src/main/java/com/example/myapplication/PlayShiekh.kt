package com.example.myapplication

/**
 * //TODO Comment why you use isPLaying and isLoading
 */
data class PlayShiekh(
    val id : Int,
    val name: String,
    val imageUrl: String,
    val pathOrUrl: String,
    val timerStart: String = "00:00",
    val timerEnd: String,
    var isPlaying: Boolean = false,
    val isLoading : Boolean = false,
)