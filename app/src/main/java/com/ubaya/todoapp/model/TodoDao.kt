package com.ubaya.todoapp.model

import androidx.room.*

@Dao
interface TodoDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE )
   suspend fun insertAll(vararg todo:Todo)

   @Query("Select * From todo")
   suspend fun selectAllTodo() : List<Todo>

   @Query("Select * From todo where is_done = 0")
   suspend fun selectNotDoneTodo() : List<Todo>

   @Query("Select * from todo where uuid = :id")
   suspend fun selectTodo(id:Int):Todo

   @Delete
   suspend fun deleteTodo(todo:Todo)

   @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid = :id")
   suspend fun update(title:String, notes:String, priority:Int, id:Int)

   @Query("UPDATE todo SET is_done=1 WHERE uuid = :id")
   suspend fun taskChecked(id:Int)
}