package todo

import io.ktor.server.application.*
import todo.db.DatabaseFactory
import todo.db.TodoDao
import todo.db.TodoUserDaoImpl
import todo.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)
@Suppress("unused")
fun Application.module() {
    val dao: TodoDao = TodoUserDaoImpl()
    DatabaseFactory.init()
    configureKoin()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting(dao = dao)



}
