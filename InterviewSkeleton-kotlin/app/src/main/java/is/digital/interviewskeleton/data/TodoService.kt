package `is`.digital.interviewskeleton.data

import `is`.digital.interviewskeleton.data.dto.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TodoService {
    @GET("task")
    fun getTodos(): Single<GetResponse?>?

    @POST("task")
    fun addTodo(@Body request: AddRequest): Single<AddResponse?>?

    @PUT("task/{task}")
    fun updateTodo(@Path("task") taskId: String?, @Body request: CompleteRequest?): Single<CompleteResponse?>?

    @DELETE("task/{task}")
    fun deleteTodo(@Path("task") taskId: String?): Single<DeleteResponse?>?

}