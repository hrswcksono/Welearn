package com.tugasakhir.welearn.domain.entity

data class SoalEntity(
    val idSoal: Int,
    val idJenisSoal: Int,
    val idLevel: Int,
    val soal: String,
    val keterangan: String,
    val jawaban: String
)