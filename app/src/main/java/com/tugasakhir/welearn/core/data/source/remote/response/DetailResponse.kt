package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: DMessage? = null
)

data class DMessage(

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("angka")
	val angka: Int? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
