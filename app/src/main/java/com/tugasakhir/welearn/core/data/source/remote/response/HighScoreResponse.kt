package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HighScoreResponse(

	@field:SerializedName("success")
	val success: String,

	@field:SerializedName("message")
	val message: List<HMessageItem>
)

data class HMessageItem(

	@field:SerializedName("total")
	val total: String,

	@field:SerializedName("name")
	val name: String
)
