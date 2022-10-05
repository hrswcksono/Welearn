package com.tugasakhir.welearn.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tugasakhir.welearn.core.data.source.local.entity.Level
import com.tugasakhir.welearn.core.data.source.local.entity.Soal
import com.tugasakhir.welearn.core.data.source.local.entity.UserParticipant
import kotlinx.coroutines.flow.Flow

@Dao
interface WelearnDao {

    @Query("SELECT * FROM level where idJenis = :id")
    fun getLevelSoal(id : Int): Flow<List<Level>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevel(level: List<Level>)

    @Query("SELECT * FROM soal where idJenis = :id AND idLevel = :level")
    fun getSoal(id: Int, level: Int): Flow<List<Soal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoal(soal: List<Soal>)

    @Query("SELECT * FROM user_participant where idGame = :id")
    fun getUserParticipant(id: Int): Flow<List<UserParticipant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserParticipant(user: List<UserParticipant>)
}