package com.example.todolist.screen

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object AddTodo: Screen("addTodo")
}
