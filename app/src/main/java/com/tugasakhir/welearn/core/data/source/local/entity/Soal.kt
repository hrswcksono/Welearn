package com.tugasakhir.welearn.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soal")
data class Soal(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idSoal")
    val idSoal: Int,

    @ColumnInfo(name = "idJenis")
    val idJenis: Int,

    @ColumnInfo(name = "idLevel")
    val idLevel: Int,

    @ColumnInfo(name = "soal")
    val soal: String,

    @ColumnInfo(name = "keterangan")
    val keterangan: String,

    @ColumnInfo(name = "jawaban")
    val jawaban: String
)