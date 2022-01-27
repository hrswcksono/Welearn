package com.tugasakhir.welearn.domain.model

data class User(
    val id_user: Int,
    val email: String,
    val name: String,
    val username: String,
    val password: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String
)
