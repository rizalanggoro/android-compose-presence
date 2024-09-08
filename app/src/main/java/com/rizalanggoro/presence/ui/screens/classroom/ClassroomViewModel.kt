package com.rizalanggoro.presence.ui.screens.classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.entities.Classroom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(
    private val classroomDao: ClassroomDao,
) : ViewModel() {
    val classrooms = classroomDao.getAll()

    fun deleteClassroom(classroom: Classroom) {
        viewModelScope.launch {
            classroomDao.delete(classroom)
        }
    }
}