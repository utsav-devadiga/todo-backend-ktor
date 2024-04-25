package todo.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import todo.models.Todo
import todo.models.TodoApiResponse

class TodoUserDaoImpl : TodoDao {

    override suspend fun getAllTodo(userID: String): TodoApiResponse {

        val todos = DatabaseFactory.dbQuery {
            Todos.select { Todos.userid eq userID }.map { toTodo(it) }
        }
        val currentTimestamp = System.currentTimeMillis()

        return TodoApiResponse(
            success = true,
            message = "Data found",
            todo = todos,
            lastUpdated = currentTimestamp
        )


    }

    override suspend fun getTodo(id: Int): Todo? {
        return DatabaseFactory.dbQuery {
            Todos.select { Todos.id eq id }
                .mapNotNull { toTodo(it) }
                .singleOrNull()
        }
    }

    override suspend fun addTodo(todo: Todo): Boolean {
        return DatabaseFactory.dbQuery {
            Todos.insert {
                it[name] = todo.name
                it[about] = todo.about
                it[priority] = todo.priority
                it[deadline] = todo.deadline
                it[userid] = todo.userid
                it[status] = todo.status
                it[statusTimeStamp] = todo.statusTimestamp
            }.insertedCount > 0
        }
    }

    override suspend fun editTodo(id: Int, status: Int): Boolean {
        return DatabaseFactory.dbQuery {
            Todos.update({ Todos.id eq id }) {
                it[this.status] = status
                it[this.statusTimeStamp] = System.currentTimeMillis().toString()
            } > 0
        }
    }

    override suspend fun deleteTodo(id: Int): Boolean {
        return DatabaseFactory.dbQuery {
            Todos.deleteWhere { Todos.id eq id } > 0
        }
    }

    private fun toTodo(row: ResultRow): Todo =
        Todo(
            id = row[Todos.id],
            name = row[Todos.name],
            about = row[Todos.about],
            priority = row[Todos.priority],
            deadline = row[Todos.deadline],
            userid = row[Todos.userid],
            status = row[Todos.status],
            statusTimestamp = row[Todos.statusTimeStamp]
        )
}
