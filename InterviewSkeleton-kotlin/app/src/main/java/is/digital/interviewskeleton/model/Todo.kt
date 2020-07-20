package `is`.digital.interviewskeleton.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todo(
        @field:Json(name = "_id")
        val todoId: String,
        val completed: Boolean,
        val description: String
)