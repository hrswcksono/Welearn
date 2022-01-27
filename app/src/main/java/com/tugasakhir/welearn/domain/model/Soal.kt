package com.tugasakhir.welearn.domain.model

data class Soal(
    val id_soal: Int,
    val id_jenis_soal: Int,
    val id_level: Int,
    val soal: String,
    val keterangan: String,
    val jawaban: String
)