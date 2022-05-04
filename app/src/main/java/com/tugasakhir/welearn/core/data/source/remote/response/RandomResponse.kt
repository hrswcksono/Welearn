package com.tugasakhir.welearn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RandomResponse(

	@field:SerializedName("success")
	val success: String,

	@field:SerializedName("response_time")
	val responseTime: Double,

	@field:SerializedName("message")
	val message: List<MessageItem>
)

data class MessageItem(

	@field:SerializedName("soal")
	val soal: String,

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("id_jenis")
	val idJenis: Int,

	@field:SerializedName("id_soal")
	val idSoal: Int,

	@field:SerializedName("id_level")
	val idLevel: Int,

	@field:SerializedName("jawaban")
	val jawaban: String
)
