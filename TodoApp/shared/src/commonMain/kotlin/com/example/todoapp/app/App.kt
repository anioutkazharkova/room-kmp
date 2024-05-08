package com.example.todoapp.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.todoapp.AddTodoView
import com.example.todoapp.AddTodoViewModel
import com.example.todoapp.screen.TodoList
import com.example.todoapp.TodoViewModel
import com.example.todoapp.di.KoinF
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition



enum class TaskScreen{
    TaskList,
    TaskItem
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: TaskScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { currentScreen.name },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        })
}

@Composable
fun App() {
    PreComposeApp {
        val navigator = rememberNavigator()
        val modifier = Modifier
        val backStackEntry by navigator.currentEntry.collectAsState(null)
        KoinF.setupKoin()
        // Get the name of the current screen
        val currentScreen = TaskScreen.valueOf(
            backStackEntry?.route?.route ?: TaskScreen.TaskList.name
        )
        val canNavigateBack by navigator.canGoBack.collectAsState(false)
        Scaffold(
            topBar = {
                AppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = canNavigateBack,
                    navigateUp = { navigator.goBack() }
                )
            }
        ) { paddingValues ->

            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                // The start destination
                initialRoute = TaskScreen.TaskList.name
            ) {
                scene(
                    route = TaskScreen.TaskList.name,
                    navTransition = NavTransition()
                ) {
                    TodoList(KoinF.di?.get<TodoViewModel>()!!, modifier) {
                        navigator.navigate(TaskScreen.TaskItem.name)
                    }
                }
                scene(route = TaskScreen.TaskItem.name) {
                    AddTodoView(KoinF.di?.get<AddTodoViewModel>()!!, modifier) {
                        navigator.goBack()
                    }
                }
            }
        }
    }
}