package com.tugasakhir.welearn.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tugasakhir.welearn.core.data.source.local.entity.Level
import com.tugasakhir.welearn.core.data.source.local.entity.Soal
import com.tugasakhir.welearn.core.data.source.local.entity.UserParticipant

@Database(entities = [Level::class, Soal::class, UserParticipant::class], version = 1, exportSchema = false)
abstract class WelearnDatabase : RoomDatabase() {

    abstract fun welearnDao(): WelearnDao

}