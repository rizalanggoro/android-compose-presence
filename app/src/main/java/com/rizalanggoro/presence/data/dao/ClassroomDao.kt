package com.rizalanggoro.presence.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.rizalanggoro.presence.data.entities.Classroom

@Dao
interface ClassroomDao {
    @Insert
    suspend fun insert(classroom: Classroom): Long

    @Query("select * from classrooms where classroom_id = :classroomId limit 1")
    fun get(classroomId: Long): LiveData<Classroom>

    @Query("select * from classrooms order by classroom_name")
    fun getAll(): LiveData<List<Classroom>>
    
    @Update
    suspend fun update(classroom: Classroom)

    @Delete
    suspend fun delete(classroom: Classroom)
}