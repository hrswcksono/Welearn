package com.tugasakhir.welearn.domain.entity

data class Login(
    val status: String,
    val token:String ?= null,
    val name: String ?= null,
    val id: Int ?= null,
)