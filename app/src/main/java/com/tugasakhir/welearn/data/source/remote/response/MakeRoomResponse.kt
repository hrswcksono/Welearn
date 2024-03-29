package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MakeRoomResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: RoomResponse? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class RoomResponse(

	@field:SerializedName("id_game")
	val idGame: Int? = null,

	@field:SerializedName("id_room")
	val idRoom: Int? = null
)
