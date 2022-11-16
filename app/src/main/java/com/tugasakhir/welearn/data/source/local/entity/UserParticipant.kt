package com.tugasakhir.welearn.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_participant")
data class UserParticipant(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "idGame")
    val idGame: Int,

    @ColumnInfo(name = "username")
    val username: String,
)
