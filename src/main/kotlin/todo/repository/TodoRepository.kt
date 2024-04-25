package todo.repository

import todo.models.Todo
import todo.models.TodoApiResponse


interface TodoRepository {

    suspend fun getAllTodoes(page: Int = 1): TodoApiResponse
    suspend fun searchTodoes(name: String?): TodoApiResponse

}