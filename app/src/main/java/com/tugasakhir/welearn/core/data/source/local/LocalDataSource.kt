package com.tugasakhir.welearn.core.data.source.local

import com.tugasakhir.welearn.core.data.source.local.entity.Level
import com.tugasakhir.welearn.core.data.source.local.entity.Soal
import com.tugasakhir.welearn.core.data.source.local.entity.UserParticipant
import com.tugasakhir.welearn.core.data.source.local.room.WelearnDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val welearnDao: WelearnDao) {

    fun getLevel(id : Int): Flow<List<Level>> = welearnDao.getLevelSoal(id)

    suspend fun insertLevel(level: List<Level>) = welearnDao.insertLevel(level)

    fun getSoal(id : Int, level: Int): Flow<List<Soal>> = welearnDao.getSoal(id, level)

    suspend fun insertSoal(soal: List<Soal>) = welearnDao.insertSoal(soal)

    fun getUserParticipant(id: Int): Flow<List<UserParticipant>> = welearnDao.getUserParticipant(id)

    suspend fun insertUserParticipant(user: List<UserParticipant>) = welearnDao.insertUserParticipant(user)

}