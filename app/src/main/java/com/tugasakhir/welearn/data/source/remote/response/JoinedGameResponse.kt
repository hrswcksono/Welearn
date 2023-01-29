package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListJoinedGameResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("data")
	val data: List<JoinedGameResponse>
)

data class JoinedGameResponse(

	@field:SerializedName("id_game")
	val idGame: Int? = null,

	@field:SerializedName("pembuat_room")
	val pembuat_room: String? = null,

	@field:SerializedName("jenis_soal")
	val jenis_soal: String? = null,

	@field:SerializedName("id_level")
	val id_level: Int? = null,
)
