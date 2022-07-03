package com.tugasakhir.welearn.domain.model

data class Soal(
    val idSoal: Int,
    val idJenisSoal: Int,
    val idLevel: Int,
    val soal: String,
    val keterangan: String,
    val jawaban: String
)