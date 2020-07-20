package `is`.digital.interviewskeleton.data.dto

import `is`.digital.interviewskeleton.model.Todo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AddResponse (val success: Boolean, val data: Todo)