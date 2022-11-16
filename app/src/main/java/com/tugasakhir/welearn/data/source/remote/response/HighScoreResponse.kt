package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListHighScoreResponse(

	@field:SerializedName("success")
	val success: String,

	@field:SerializedName("message")
	val message: List<HighScoreResponse>
)

data class HighScoreResponse(

	@field:SerializedName("total")
	val total: String,

	@field:SerializedName("name")
	val name: String
)
