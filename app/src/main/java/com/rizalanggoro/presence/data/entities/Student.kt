package com.rizalanggoro.presence.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nis") val nis: String,
    @ColumnInfo(name = "name") val name: String,
)
