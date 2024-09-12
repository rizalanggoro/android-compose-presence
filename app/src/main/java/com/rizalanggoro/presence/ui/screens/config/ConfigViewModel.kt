package com.rizalanggoro.presence.ui.screens.config

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.rizalanggoro.presence.data.repositories.ExcelFileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val excelFileRepository: ExcelFileRepository,
) : ViewModel() {


    fun handleFileUpload(uri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            excelFileRepository.handleImport(uri)
        }

        CoroutineScope(Dispatchers.IO).launch {
//            val inputStream = context.contentResolver.openInputStream(uri)
//            if (inputStream != null) {
//                val workbook = WorkbookFactory.create(inputStream)
//
//                // get classrooms
//                val classrooms = arrayListOf<String>()
//                val sheetClassrooms = workbook.getSheetAt(0)
//                for (row in sheetClassrooms) {
//                    for (cell in row) {
//                        val value = cell.toString()
//                        if (value == "name") continue
//                        classrooms.add(value)
//                    }
//                }
//
//                println(classrooms)
//
//                inputStream.close()
//            }
        }
    }
}