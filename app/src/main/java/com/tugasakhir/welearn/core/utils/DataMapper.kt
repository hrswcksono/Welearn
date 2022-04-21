package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.core.data.source.remote.response.Message
import com.tugasakhir.welearn.domain.model.Login

object DataMapper {
    fun mapperLoginToken(it: Message) = Login(
        token = it.token.toString()
    )
}