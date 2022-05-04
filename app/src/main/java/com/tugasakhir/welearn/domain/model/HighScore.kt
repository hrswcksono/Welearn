package com.tugasakhir.welearn.domain.model

data class HighScore(
    val listScore: List<UserScore>
)

data class UserScore(
    val name: String = "",
    val total: String = ""
)
