package com.rizalanggoro.presence.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rizalanggoro.presence.data.entities.Student

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Insert
    suspend fun insertAll(students: List<Student>)

    @Query("select * from students where classroom_id = :classroomId order by student_nis")
    fun getAllByClassroom(classroomId: Long): LiveData<List<Student>>
}