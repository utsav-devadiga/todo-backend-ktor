package todo.db

import todo.models.Todo
import todo.models.TodoApiResponse

interface TodoDao {
    suspend fun getAllTodo(userID: String): TodoApiResponse
    suspend fun getTodo(id: Int): Todo?
    suspend fun addTodo(todo: Todo): Boolean
    suspend fun editTodo(id: Int, status: Int): Boolean
    suspend fun deleteTodo(id: Int): Boolean
}