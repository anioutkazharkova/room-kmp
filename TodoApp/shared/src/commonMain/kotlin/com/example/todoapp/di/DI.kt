package com.example.todoapp.di

import com.example.todoapp.AddTodoViewModel
import com.example.todoapp.TodoViewModel
import com.example.todoapp.repo.TaskRepository
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import kotlin.reflect.KClass

expect fun platformModule(): Module

fun vmModule(): Module {
    return module {
        single { TaskRepository(get()) }
        factory { AddTodoViewModel(get()) }
        factory { TodoViewModel(get()) }
    }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(platformModule(), vmModule())

    }

object KoinF {
    var di: Koin? = null

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration).koin
        }
    }
}