package com.rizalanggoro.presence.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "classrooms",
)
data class Classroom(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "classroom_id")
    val id: Long = 0,

    @ColumnInfo(name = "classroom_name")
    val name: String,
)
