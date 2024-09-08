package com.rizalanggoro.presence.data.source.local

import android.app.Application
import androidx.room.Room

class PresenceDatabaseConfig {
    companion object {
        fun instance(application: Application): PresenceDatabase {
            return Room.databaseBuilder(
                application,
                PresenceDatabase::class.java,
                "presence_database"
            ).build()
        }
    }
}