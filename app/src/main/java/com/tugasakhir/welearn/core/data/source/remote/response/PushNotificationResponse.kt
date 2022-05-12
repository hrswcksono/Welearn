package com.tugasakhir.welearn.core.data.source.remote.response

data class PushNotificationResponse(
	val canonicalIds: Int? = null,
	val success: Int? = null,
	val failure: Int? = null,
	val results: List<ResultsItem?>? = null,
	val multicastId: Long? = null
)

data class ResultsItem(
	val messageId: String? = null
)

