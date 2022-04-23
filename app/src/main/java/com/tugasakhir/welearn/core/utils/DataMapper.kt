package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.core.data.source.remote.response.DMessage
import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.core.data.source.remote.response.Message
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.model.User

object DataMapper {
    fun mapperLoginToken(it: Message) = Login(
        token = it.token.toString()
    )

    fun mapperDetailUser(it: DMessage) = User(
        username = it.username.toString(),
        email = it.email.toString(),
        jenis_kelamin = it.jenisKelamin.toString(),
        score = it.score.toString(),
        angka = it.angka.toString()
    )
}