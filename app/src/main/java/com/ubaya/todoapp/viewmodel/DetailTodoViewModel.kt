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

class DetailTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
        private val job = Job()
    val todoLD = MutableLiveData<Todo>()
    fun addTodo(list:List<Todo>) {
        launch {
            val db = buildDb(getApplication())
            db.tododao().insertAll(*list.toTypedArray())
        }
    }
    fun fetch(uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            todoLD.value =  db.tododao().selectTodo(uuid)
        }
    }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}