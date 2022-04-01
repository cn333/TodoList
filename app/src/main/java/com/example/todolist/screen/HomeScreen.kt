package com.example.todolist.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todolist.database.TodoItem
import com.example.todolist.database.TodoViewModel
import com.example.todolist.database.TodoViewModelFactory

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    val items = viewModel.readAllData.observeAsState(listOf()).value

    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text(text = "Todo List") }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MenuCard(navController, viewModel)
            TodoList(list = items, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoList(
    list: List<TodoItem>,
    viewModel: TodoViewModel
) {
    LazyColumn {
        items(list) { item ->
            val isCompleted = remember { mutableStateOf(item.isCompleted) }

            ListItem(
                text = { Text(text = item.itemName) },
                icon = {
                    IconButton(onClick = {
                        viewModel.deteteTodo(item)
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Todo Item"
                        )
                    }
                },
                trailing = {
                    Checkbox(
                        checked = isCompleted.value,
                        onCheckedChange = {
                            isCompleted.value = it
                            item.isCompleted = it
                            viewModel.updateTodo(item)
                        }
                    )
                }
            )
            Divider()
        }
    }
}

@Composable
fun MenuCard(
    navController: NavHostController,
    viewModel: TodoViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SimpleButton(text = "Add Todo", onClick = {
            navController.navigate(Screen.AddTodo.route)
        })
        SimpleButton(text = "Clear All", onClick = {
            viewModel.deleteAllTodos()
        })
    }
}

@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}