package todo.routes


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import todo.repository.TodoRepository

fun Route.searchTodo() {

    val heroRepository: TodoRepository by inject()


    get("/todo/search") {
        val name = call.request.queryParameters["name"]

        val apiResponse = heroRepository.searchTodoes(name = name)
        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )

    }
}