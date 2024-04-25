package todo.repository

import todo.models.Todo
import todo.models.TodoApiResponse


interface TodoRepositoryAlternative {

    val heroes: List<Todo>

    suspend fun getAllHeroes(page: Int = 1, limit: Int = 4): TodoApiResponse
    suspend fun searchHeroes(name: String?): TodoApiResponse

}