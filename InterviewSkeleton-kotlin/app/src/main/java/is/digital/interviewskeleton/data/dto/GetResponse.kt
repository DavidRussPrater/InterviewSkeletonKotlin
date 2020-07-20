package `is`.digital.interviewskeleton.data.dto

import `is`.digital.interviewskeleton.model.Todo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GetResponse (val count: Int, val data: List<Todo>)