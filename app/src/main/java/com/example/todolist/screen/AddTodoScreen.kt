package com.example.todolist.screen

import android.app.Application
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todolist.database.TodoItem
import com.example.todolist.database.TodoViewModel
import com.example.todolist.database.TodoViewModelFactory

@Composable
fun AddTodoScreen(
    navController: NavHostController
) {
    val todoViewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val inputViewModel: InputViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Add New Todo")
            })
        },
        floatingActionButton = {
            FABComponent(text = "Save Todo") {
                insertTodoInDB(inputViewModel.todo.value.toString(), todoViewModel)
                navController.popBackStack()
            }
        }
    ) {
        NewTodo(inputViewModel)
    }
}

@Composable
fun NewTodo(viewModel: InputViewModel) {
    val todoName = viewModel.todo.observeAsState("")

    InputField(todoName.value) {
        viewModel.onInputChange(it)
    }
}

@Composable
private fun InputField(
    name: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    InputTextFieldComponent(
        text = name,
        onChange = onValueChange,
        label = "Enter todo",
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        keyboardAction = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Composable
fun FABComponent(
    text: String,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        text = { Text(text) }, 
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

private fun insertTodoInDB(
    todo: String,
    viewModel: TodoViewModel
) {
    if (todo.isNotEmpty()) {
        val todoItem = TodoItem(itemName = todo, isCompleted = false)
        viewModel.addTodo(todoItem)
    }
}

@Composable
private fun InputTextFieldComponent(
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean =  true,
    label: String = "Some value",
    keyboardAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = text,
        onValueChange = onChange,
        modifier = modifier,
        singleLine = singleLine,
        label = {
            Text(text = label)
        },
        keyboardActions = keyboardAction
    )
}

class InputViewModel: ViewModel() {
    private val _todo: MutableLiveData<String> = MutableLiveData("")
    val todo: LiveData<String> = _todo

    fun onInputChange(newName: String) {
        _todo.value = newName
    }
}