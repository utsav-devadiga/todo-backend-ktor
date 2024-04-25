package todo.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val database = Database.connect(
            url = "TODO-RDS-EBS-LOCATION_HERE",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "root!root"
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