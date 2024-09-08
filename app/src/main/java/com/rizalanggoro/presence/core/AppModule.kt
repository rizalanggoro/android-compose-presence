package com.rizalanggoro.presence.core

import android.app.Application
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.dao.StudentDao
import com.rizalanggoro.presence.data.source.local.PresenceDatabaseConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideClassroomDao(application: Application): ClassroomDao =
        PresenceDatabaseConfig.instance(application).classroomDao

    @Provides
    @Singleton
    fun provideStudentDao(application: Application): StudentDao =
        PresenceDatabaseConfig.instance(application).studentDao
}