package `is`.digital.interviewskeleton.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DeleteResponse(success: Boolean, data: Any?, message: String?) {
    val success: Boolean
    val data: Any?
    val message: String?

    init {
        this.success = success
        this.data = data
        this.message = message
    }
}