package com.rizalanggoro.presence.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    @ColumnInfo(name = "student_nis")
    val nis: String,

    @ColumnInfo(name = "student_name")
    val name: String,

    @ColumnInfo(name = "classroom_id")
    val classroomId: Long,
)
