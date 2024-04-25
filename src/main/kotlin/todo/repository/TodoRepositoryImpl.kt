package todo.repository

import todo.models.Todo
import todo.models.TodoApiResponse


const val NEXT_PAGE_KEY = "nextPage"
const val PREVIOUS_PAGE_KEY = "prevPage"

class TodoRepositoryImpl : TodoRepository {


    override suspend fun getAllTodoes(page: Int): TodoApiResponse {
        return TodoApiResponse(
            success = true,
            message = "Data found",
            prevPage = calculatePage(page = page)[PREVIOUS_PAGE_KEY],
            nextPage = calculatePage(page = page)[NEXT_PAGE_KEY],
            todo = emptyList(),
            lastUpdated = System.currentTimeMillis()
        )
    }

    private fun calculatePage(page: Int) =
        mapOf(
            PREVIOUS_PAGE_KEY to if (page in 2..5) page.minus(1) else null,
            NEXT_PAGE_KEY to if (page in 1..4) page.plus(1) else null
        )

    override suspend fun searchTodoes(name: String?): TodoApiResponse {
        val Todoes = findTodoes(query = name)
        return TodoApiResponse(
            success = Todoes.isNotEmpty(),
            message = if (Todoes.isNotEmpty()) "Todoes found" else "Data not found",
            todo = Todoes
        )
    }


    private fun findTodoes(query: String?): List<Todo> {
        val founded = mutableListOf<Todo>()
        return if (!query.isNullOrEmpty()) {
            emptyList()
        } else {
            emptyList()
        }
    }
}