package com.rizalanggoro.presence.ui.screens.classroom.create

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalanggoro.presence.core.UiStateStatus
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.entities.Classroom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateClassroomViewModel @Inject constructor(
    private val application: Application,
    private val classroomDao: ClassroomDao,
) : ViewModel() {
    var uiState by mutableStateOf(CreateClassroomUiState())

    var name by mutableStateOf("")

    fun createClassroom() {
        try {
            if (name.isEmpty()) throw Exception("Nama kelas tidak boleh kosong!")

            viewModelScope.launch {
                classroomDao.insert(
                    Classroom(name = name)
                )

                launch {
                    uiState = uiState.copy(
                        action = CreateClassroomUiState.Action.Create,
                        status = UiStateStatus.Success,
                    )
                }
            }
        } catch (e: Exception) {
            uiState = uiState.copy(
                action = CreateClassroomUiState.Action.Create,
                status = UiStateStatus.Failure,
                message = e.message ?: "Terjadi kesalahan saat membuat kelas",
                failureType = CreateClassroomUiState.FailureType.Name,
            )
        }

    }
}