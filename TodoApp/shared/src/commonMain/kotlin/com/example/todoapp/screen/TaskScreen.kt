package com.example.todoapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.TodoViewModel
import com.example.todoapp.db.TodoEntity

@Composable
fun TodoList(viewModel: TodoViewModel, modifier: Modifier = Modifier, onAdd: () -> Unit) {
    val tasks by viewModel.tasks.collectAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd.invoke() },
                modifier = modifier
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "TODOs", fontSize = 22.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            TaskView(tasks = tasks, modifier)
        }
    }
}

@Composable
fun TaskView(tasks: List<TodoEntity>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = // 1.
        PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(tasks) {
            Text(it.title)
        }
    }
}


