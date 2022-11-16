package com.tugasakhir.welearn.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tugasakhir.welearn.data.source.local.entity.UserParticipant
import kotlinx.coroutines.flow.Flow

@Dao
interface WelearnDao {

    @Query("SELECT * FROM user_participant where idGame = :id")
    fun getUserParticipant(id: Int): Flow<List<UserParticipant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserParticipant(user: List<UserParticipant>)
}