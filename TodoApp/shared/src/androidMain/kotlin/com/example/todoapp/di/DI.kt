package com.example.todoapp.di

import com.example.todoapp.db.AppDatabase
import com.example.todoapp.db.getDatabase
import org.koin.dsl.module


actual fun platformModule() = module {
    single<AppDatabase> { getDatabase(get()) }
}
