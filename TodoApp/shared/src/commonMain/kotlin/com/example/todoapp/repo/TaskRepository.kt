package com.example.todoapp.repo

import com.example.todoapp.db.AppDatabase
import com.example.todoapp.db.TodoDao
import com.example.todoapp.db.TodoEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val database: AppDatabase) {
private val dao: TodoDao by lazy {
    database.getDao()
}

    suspend fun addTodo(todoEntity: TodoEntity) {
        dao.insert(todoEntity)
    }

    suspend fun loadTodos(): Flow<List<TodoEntity>> {
        return dao.getAllAsFlow()
    }
}