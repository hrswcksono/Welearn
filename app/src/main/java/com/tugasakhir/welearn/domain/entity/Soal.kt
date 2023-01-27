package com.tugasakhir.welearn.domain.entity

data class Soal(
    val idSoal: Int,
    val idJenis: Int,
    val idLevel: Int,
    val soal: String,
    val keterangan: String,
    val jawaban: String
)