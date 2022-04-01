package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance =  Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java, "todo_database"
                ).build()

                INSTANCE = instance
            }
            return instance
        }
    }
}