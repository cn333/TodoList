package com.example.todolist.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_item")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "item_name")
    var itemName: String,

    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean = false
)
