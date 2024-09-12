package com.rizalanggoro.presence.ui.screens.classroom.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalanggoro.presence.core.PresenceStatus
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.dao.PresenceDao
import com.rizalanggoro.presence.data.dao.StudentDao
import com.rizalanggoro.presence.data.entities.Presence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DetailClassroomViewModel @Inject constructor(
    private val classroomDao: ClassroomDao,
    private val studentDao: StudentDao,
    private val presenceDao: PresenceDao,
) : ViewModel() {
    var currentDate by mutableLongStateOf(System.currentTimeMillis())
        private set

    fun classroom(classroomId: Long) = classroomDao.get(classroomId)
    fun students(classroomId: Long) = studentDao.getAllByClassroom(classroomId)
    fun presences(classroomId: Long) = presenceDao.getAllByClassroomId(
        classroomId = classroomId,
        date = SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault()
        ).format(currentDate)
    )

    fun updateDate(newDate: Long) {
        currentDate = newDate
    }

    fun upsertPresenceStatus(studentNis: String, status: PresenceStatus) {
        viewModelScope.launch {
            presenceDao.upsert(
                Presence(
                    studentNis = studentNis,
                    status = status,
                    date = SimpleDateFormat(
                        "yyyy-MM-dd", Locale.getDefault()
                    ).format(currentDate),
                )
            )
        }
    }
}