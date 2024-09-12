package com.rizalanggoro.presence.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rizalanggoro.presence.data.entities.Presence

@Dao
interface PresenceDao {
    @Upsert
    suspend fun upsert(presence: Presence)

    @Query(
        """
            select * from presences p
            join students s on s.student_nis = p.student_nis
            where s.classroom_id = :classroomId and
                  p.presence_date = :date
        """
    )
    fun getAllByClassroomId(classroomId: Long, date: String): LiveData<List<Presence>>
}