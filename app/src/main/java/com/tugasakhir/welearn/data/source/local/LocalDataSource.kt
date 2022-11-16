package com.tugasakhir.welearn.data.source.local

import com.tugasakhir.welearn.data.source.local.entity.UserParticipant
import com.tugasakhir.welearn.data.source.local.room.WelearnDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val welearnDao: WelearnDao) {

    fun getUserParticipant(id: Int): Flow<List<UserParticipant>> = welearnDao.getUserParticipant(id)

    suspend fun insertUserParticipant(user: List<UserParticipant>) = welearnDao.insertUserParticipant(user)

}