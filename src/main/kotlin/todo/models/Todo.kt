package todo.models

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: Int? = null,
    val name: String,
    val about: String,
    val priority: String,
    val deadline: String,
    val userid: String,
    val status: Int,
    val statusTimestamp: String
)
