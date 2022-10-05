package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListLevelResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: List<LevelResponse>
)

data class LevelResponse(

	@field:SerializedName("level_soal")
	val levelSoal: String? = null,

	@field:SerializedName("id_level")
	val idLevel: Int? = null,

	@field:SerializedName("id_jenis")
	val idJenis: Int? = null
)
