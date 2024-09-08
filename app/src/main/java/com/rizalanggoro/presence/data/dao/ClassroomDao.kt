package com.rizalanggoro.presence.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rizalanggoro.presence.data.entities.Classroom

@Dao
interface ClassroomDao {
    @Insert
    suspend fun insert(classroom: Classroom)

    @Query("SELECT * FROM classrooms")
    fun getAll(): LiveData<List<Classroom>>

    @Delete
    suspend fun delete(classroom: Classroom)
}