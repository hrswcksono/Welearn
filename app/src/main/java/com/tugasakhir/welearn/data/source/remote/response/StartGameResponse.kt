package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class StartGameResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)