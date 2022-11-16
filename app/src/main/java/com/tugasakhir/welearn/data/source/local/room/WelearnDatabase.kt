package com.tugasakhir.welearn.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tugasakhir.welearn.data.source.local.entity.UserParticipant

@Database(entities = [UserParticipant::class], version = 2, exportSchema = false)
abstract class WelearnDatabase : RoomDatabase() {

    abstract fun welearnDao(): WelearnDao

}