package com.rizalanggoro.presence.ui.screens.classroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalanggoro.presence.core.UiStateStatus
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.entities.Classroom
import com.rizalanggoro.presence.data.repositories.ExcelFileRepository
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomUiState.Action
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(
    private val classroomDao: ClassroomDao,
    private val excelFileRepository: ExcelFileRepository,
) : ViewModel() {
    val classrooms = classroomDao.getAll()
    var uiState by mutableStateOf(ClassroomUiState())
        private set

    fun resetUiState() {
        uiState = uiState.copy(
            action = Action.Initial,
            status = UiStateStatus.Initial,
        )
    }

    fun create(name: String) {
        val action = Action.Create
        if (name.isEmpty()) {
            uiState = uiState.copy(
                action = action,
                status = UiStateStatus.Failure,
                message = "Nama kelas tidak boleh kosong!"
            )
            return
        }

        viewModelScope.launch {
            classroomDao.insert(
                Classroom(
                    name = name,
                )
            )

            launch {
                uiState = uiState.copy(
                    action = action,
                    status = UiStateStatus.Success,
                )
            }
        }
    }

    fun update(classroom: Classroom) {
        val action = Action.Update
        if (classroom.name.isEmpty()) {
            uiState = uiState.copy(
                action = action,
                status = UiStateStatus.Failure,
                message = "Nama kelas tidak boleh kosong!"
            )
            return
        }

        viewModelScope.launch {
            classroomDao.update(classroom)

            launch {
                uiState = uiState.copy(
                    action = action,
                    status = UiStateStatus.Success,
                )
            }
        }
    }

    fun delete(classroom: Classroom) {
        val action = Action.Delete
        viewModelScope.launch {
            classroomDao.delete(classroom)

            launch {
                uiState = uiState.copy(
                    action = action,
                    status = UiStateStatus.Success,
                )
            }
        }
    }
}