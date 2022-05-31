package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScoreResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: ScoreMessage? = null
)

data class ScoreMessage(

	@field:SerializedName("score")
	val score: Int? = null
)
