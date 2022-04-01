package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoItemDao {
    @Query("SELECT * from todo_item")
    fun getAll(): LiveData<List<TodoItem>>

    @Insert
    suspend fun insert(item: TodoItem)

    @Update
    suspend fun update(item: TodoItem)

    @Delete
    suspend fun delete(item: TodoItem)

    @Query("DELETE FROM todo_item")
    suspend fun deleteAllTodos()
}