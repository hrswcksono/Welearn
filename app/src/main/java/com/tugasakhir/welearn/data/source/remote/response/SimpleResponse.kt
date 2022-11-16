package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SimpleResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
