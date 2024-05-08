package com.example.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.db.TodoEntity
import com.example.todoapp.repo.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddTodoViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val titleText: MutableStateFlow<String> = MutableStateFlow<String>("")
    fun onConfirm() {
        viewModelScope.launch {
            taskRepository.addTodo(TodoEntity(title = titleText.value, content = "", date = ""))
        }
    }
}