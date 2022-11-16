package com.tugasakhir.welearn.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SoalResponse(

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: SMessage? = null
)

data class SMessage(

	@field:SerializedName("soal")
	val soal: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("id_jenis")
	val idJenis: Int? = null,

	@field:SerializedName("id_soal")
	val idSoal: Int? = null,

	@field:SerializedName("id_level")
	val idLevel: Int? = null,

	@field:SerializedName("jawaban")
	val jawaban: String? = null
)
