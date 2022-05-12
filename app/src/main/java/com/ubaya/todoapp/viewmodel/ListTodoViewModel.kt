package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.model.TodoDatabase
import com.ubaya.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application)
    :AndroidViewModel(application),CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())
            todoLD.value = db.tododao().selectAllTodo()

        }
    }
    // Untuk menghapus sebuah item dari database
    fun clearTask(todo:Todo) {
        launch {
            val db = buildDb(getApplication())
            db.tododao().deleteTodo(todo)
//            db.tododao().taskChecked(id)
            todoLD.value = db.tododao().selectAllTodo()
        }
    }
    fun doneTask(id:Int) {
        launch {
            val db = buildDb(getApplication())
            db.tododao().taskChecked(id)
            todoLD.value = db.tododao().selectAllTodo()
        }
    }
}