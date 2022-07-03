package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScoreMultiplayerResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: List<ScoreMultiItem>
)

data class ScoreMultiItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)
