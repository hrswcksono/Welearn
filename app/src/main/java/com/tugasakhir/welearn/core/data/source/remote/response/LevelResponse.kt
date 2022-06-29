package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LevelResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: List<MessageLevel>
)

data class MessageLevel(

	@field:SerializedName("level_soal")
	val levelSoal: String? = null,

	@field:SerializedName("id_level")
	val idLevel: Int? = null
)
