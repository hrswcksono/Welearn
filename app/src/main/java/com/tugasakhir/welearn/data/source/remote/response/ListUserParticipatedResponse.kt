package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListUserParticipatedResponse(

	@field:SerializedName("success")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("data")
	val data: List<UserParticipatedResponse>
)

data class UserParticipatedResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_game")
	val idGame: Int? = null
)
