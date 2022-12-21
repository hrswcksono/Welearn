package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListJoinedGameResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: List<JoinedGameResponse>
)

data class JoinedGameResponse(

	@field:SerializedName("id_game")
	val idGame: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("jenis_soal")
	val jenis_soal: String? = null,

	@field:SerializedName("id_level")
	val id_level: Int? = null,
)
