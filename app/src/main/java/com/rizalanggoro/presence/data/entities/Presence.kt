package com.rizalanggoro.presence.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.rizalanggoro.presence.core.PresenceStatus

@Entity(
    tableName = "presences",
    primaryKeys = ["student_nis", "presence_date"]
)
data class Presence(
    @ColumnInfo(name = "student_nis")
    val studentNis: String,

    @ColumnInfo(name = "presence_status")
    val status: PresenceStatus,

    @ColumnInfo(name = "presence_date")
    val date: String,

    @ColumnInfo(name = "presence_note")
    val note: String = "",
)
