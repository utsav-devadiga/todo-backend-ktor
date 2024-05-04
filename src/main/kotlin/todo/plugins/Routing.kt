package todo.plugins

import com.example.routes.root
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import todo.db.TodoDao
import todo.models.Message
import todo.models.Todo
import todo.routes.getAllTodo
import todo.routes.searchTodo

fun Application.configureRouting(dao: TodoDao) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(Message(message = cause.localizedMessage, status = false))
        }
    }
    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if (credentials.name == "todo" && credentials.password == "z7^6@tP2Y&h#Fs9D") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
    routing {
        root()
        getAllTodo()
        searchTodo()
        authenticate("auth-basic") {
            post("/posttodo") {
                val todo = call.receive<Todo>()
                dao.addTodo(todo)
                call.respond(HttpStatusCode.OK, Message(message = "TODO added successfully", status = true))
            }
        }
        authenticate("auth-basic") {
            patch("/updatetodo/{id}") {
                val todoId = call.parameters["id"]?.toIntOrNull() ?: return@patch call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid ID"
                )
                val todoUpdates = call.receive<Todo>() // Define a class with optional fields
                if (todoUpdates.status != null) {
                    val updated = dao.editTodo(todoId, status = todoUpdates.status)

                    if (updated) {
                        call.respond(HttpStatusCode.OK, Message(message = "Todo updated successfully", status = true))
                    } else {
                        call.respond(HttpStatusCode.NotFound, Message(message = "Todo not found", status = false))
                    }
                } else {
                    return@patch call.respond(
                        HttpStatusCode.BadRequest,
                        Message(message = "Status is required", status = false)
                    )
                }
            }
        }
        authenticate("auth-basic") {
            get("/alltodo") {
                val userID =
                    call.parameters["userID"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        "UserID is required"
                    )
                val listOfTodo = dao.getAllTodo(userID = userID)
                if (listOfTodo.todo.isEmpty()) {
                    call.respond(listOfTodo)
                } else {
                    call.respond(listOfTodo)
                }

            }
        }
        authenticate("auth-basic") {
            delete("/tododelete/{id}") {
                val todoId = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    Message(message = "Invalid ID", status = false)
                )

                val deleted = dao.deleteTodo(todoId)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, Message(message = "Deleted successfully", status = true))
                } else {
                    call.respond(HttpStatusCode.NotFound, Message(message = "Todo not found", status = false))
                }
            }
        }
    }
}

