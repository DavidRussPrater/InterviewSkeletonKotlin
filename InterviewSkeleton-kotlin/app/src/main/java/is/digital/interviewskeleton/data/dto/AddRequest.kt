package `is`.digital.interviewskeleton.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AddRequest(val description: String)