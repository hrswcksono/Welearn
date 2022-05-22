package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
