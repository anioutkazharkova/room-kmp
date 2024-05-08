package com.example.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.db.TodoEntity
import com.example.todoapp.repo.TaskRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks: MutableSharedFlow<List<TodoEntity>> = MutableSharedFlow(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    fun loadData() {
        viewModelScope.launch {
            repository.loadTodos().collectLatest {
                tasks.tryEmit(it)
            }
        }
    }
}