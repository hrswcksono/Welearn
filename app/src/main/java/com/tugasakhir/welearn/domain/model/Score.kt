package com.tugasakhir.welearn.domain.model

data class Score(
    val id_score: Int,
    val id_soal: Int,
    val id_user: Int,
    val score: Int,
    val date: String
)
