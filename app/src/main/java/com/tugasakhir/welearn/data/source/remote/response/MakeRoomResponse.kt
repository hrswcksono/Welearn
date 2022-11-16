package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MakeRoomResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: Room? = null
)

data class Room(

	@field:SerializedName("id")
	val id: Int? = null
)
