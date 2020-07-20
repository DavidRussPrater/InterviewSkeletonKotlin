package `is`.digital.interviewskeleton.data

import `is`.digital.interviewskeleton.data.dto.*
import `is`.digital.interviewskeleton.model.Todo
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.Single
import io.reactivex.functions.Function

class TodoRepository(var todoService: TodoService) {

    fun TodoRepository(todoService: TodoService?) {
        this.todoService = todoService!!
    }

    fun getTodos(): Single<List<Todo>>? {
        return todoService.getTodos()?.map { response -> response.data }
    }

    fun addTodo(todo: String?): Single<Todo>? {
        return todoService.addTodo(AddRequest(todo!!))
                ?.map { response: AddResponse ->
                    if (response.success) {
                        return@map response.data
                    } else {
                        throw Exception("Failed to add Todo")
                    }
                }
    }

    fun updateTodo(todo: Todo, completed: Boolean): Completable? {
        return todoService.updateTodo(todo.todoId, CompleteRequest(completed))
                ?.map{ response: CompleteResponse ->
                    if (!response.success) {
                        throw java.lang.Exception("Failed to delete Todo") // TODO: 6/22/20
                    }
                    response
                }?.ignoreElement()

    }


    fun deleteTodo(todo: Todo): Completable? {
        return todoService.deleteTodo(todo.todoId)
                ?.flatMapCompletable flatMapCompletable@{ response: DeleteResponse ->
                    if (response.success) {
                        return@flatMapCompletable Completable.complete()
                    } else {
                        throw java.lang.Exception(response.message)
                    }
                }
    }



}