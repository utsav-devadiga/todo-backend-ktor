package todo.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val message: String,
    val status: Boolean
)
