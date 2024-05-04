package todo.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import todo.models.ApplicationConstants

object DatabaseFactory {

    fun init() {

        val database = Database.connect(
            url = ApplicationConstants.URL,
            driver = ApplicationConstants.DRIVER,
            user = ApplicationConstants.USER_NAME_DATABASE,
            password = ApplicationConstants.PASSWORD_DATABASE
        )


        transaction(database) {
            SchemaUtils.create(Todos)
        }


    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}


object Todos : Table() {

    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)
    val about = varchar("about", length = 50)
    val priority = varchar("priority", length = 50)
    val deadline = varchar("deadline", length = 50)
    val userid = varchar("userid", length = 100)
    val status = integer("status")
    val statusTimeStamp = varchar("statusTimestamp", length = 50)

    override val primaryKey = PrimaryKey(id)
}