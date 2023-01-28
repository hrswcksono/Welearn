package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScoreMultiResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<ScoreMultiItem>,

	@field:SerializedName("status")
	val status: String? = null
)

data class ScoreMultiItem(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
