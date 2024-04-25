package todo.routes


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import todo.models.TodoApiResponse
import todo.repository.TodoRepository
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

fun Route.getAllTodo() {

    val heroRepository: TodoRepository by inject()

    get("/todo/todos") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)

            val apiResponse = heroRepository.getAllTodoes(page = page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = TodoApiResponse(success = false, message = "Only numbers allowed."),
                status = HttpStatusCode.BadRequest
            )

        } catch (e: IllegalArgumentException) {
            call.respond(
                message = TodoApiResponse(success = false, message = "Data not found."),
                status = HttpStatusCode.NotFound
            )
        }


    }
}