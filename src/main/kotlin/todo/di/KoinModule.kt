package todo.di



import org.koin.dsl.module
import todo.repository.TodoRepository
import todo.repository.TodoRepositoryImpl

val koinModule = module {
    single<TodoRepository> {
        TodoRepositoryImpl()
    }
}