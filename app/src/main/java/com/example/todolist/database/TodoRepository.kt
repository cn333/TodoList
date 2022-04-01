package com.example.todolist.database

import androidx.lifecycle.LiveData

class TodoRepository(private val todoItemDao: TodoItemDao) {
    val realAllData: LiveData<List<TodoItem>> = todoItemDao.getAll()

    suspend fun addTodo(todoItem: TodoItem) {
        todoItemDao.insert(todoItem)
    }

    suspend fun updateTodo(todoItem: TodoItem) {
        todoItemDao.update(todoItem)
    }

    suspend fun deleteTodo(todoItem: TodoItem) {
        todoItemDao.delete(todoItem)
    }

    suspend fun deleteAllTodos() {
        todoItemDao.deleteAllTodos()
    }
}