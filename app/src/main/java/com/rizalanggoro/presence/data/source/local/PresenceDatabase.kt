package com.rizalanggoro.presence.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.dao.StudentDao
import com.rizalanggoro.presence.data.entities.Classroom
import com.rizalanggoro.presence.data.entities.Student

@Database(
    exportSchema = false,
    version = 1,
    entities = [
        Classroom::class,
        Student::class,
    ],
)
abstract class PresenceDatabase : RoomDatabase() {
    abstract val classroomDao: ClassroomDao
    abstract val studentDao: StudentDao
}