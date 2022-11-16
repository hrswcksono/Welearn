package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListScoreResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: ScoreResponse? = null
)

data class ScoreResponse(

	@field:SerializedName("score")
	val score: Int? = null
)
