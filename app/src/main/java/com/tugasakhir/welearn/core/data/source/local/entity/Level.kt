package com.tugasakhir.welearn.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level")
data class Level(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idLevel")
    var idLevel: Int,

    @ColumnInfo(name = "levelSoal")
    var levelSoal: String,

    @ColumnInfo(name = "idJenis")
    val idJenis: Int
)
