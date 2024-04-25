package todo.models


import kotlinx.serialization.Serializable

@Serializable
data class TodoApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val todo: List<Todo> = emptyList(),
    val lastUpdated: Long? = null
)