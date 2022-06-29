package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class JoinedGameResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: List<JGameResponse>
)

data class JGameResponse(

	@field:SerializedName("id_game")
	val idGame: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("jenis_soal")
	val jenis_soal: String? = null,
)
