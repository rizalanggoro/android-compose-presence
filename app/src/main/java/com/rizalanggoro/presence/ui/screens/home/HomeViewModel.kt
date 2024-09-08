package com.rizalanggoro.presence.ui.screens.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rizalanggoro.presence.data.dao.ClassroomDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val classroomDao: ClassroomDao,
) : ViewModel() {
    private var _currentDate by mutableLongStateOf(System.currentTimeMillis())
    val currentDate: Long
        get() = _currentDate

    fun updateDate(newDate: Long) {
        _currentDate = newDate
    }
}