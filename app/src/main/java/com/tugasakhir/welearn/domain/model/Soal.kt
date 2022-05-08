package com.tugasakhir.welearn.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Soal(
    val id_soal: Int = 0,
    val id_jenis_soal: Int = 0,
    val id_level: Int = 0,
    val soal: String = "",
    val keterangan: String = "",
    val jawaban: String = ""
): Parcelable