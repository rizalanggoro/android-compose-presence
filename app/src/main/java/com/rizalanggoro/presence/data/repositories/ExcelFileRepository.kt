package com.rizalanggoro.presence.data.repositories

import android.app.Application
import android.net.Uri
import com.rizalanggoro.presence.data.dao.ClassroomDao
import com.rizalanggoro.presence.data.dao.StudentDao
import com.rizalanggoro.presence.data.entities.Classroom
import com.rizalanggoro.presence.data.entities.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory

class ExcelFileRepository(
    private val application: Application,
    private val classroomDao: ClassroomDao,
    private val studentDao: StudentDao,
) {
    suspend fun handleImport(uri: Uri) {
        val inputStream = application.contentResolver.openInputStream(uri)
        if (inputStream != null) {
            val workbook = WorkbookFactory.create(inputStream)

            // get classrooms
            val classroomNames = arrayListOf<String>()
            val sheetClassrooms = workbook.getSheetAt(0)
            for ((index, row) in sheetClassrooms.withIndex()) {
                if (index == 0) continue
                for (cell in row) {
                    classroomNames.add(cell.stringCellValue)
                }
            }

            // get all students per classroom
            for (classroomName in classroomNames) {
                val classroom = Classroom(name = classroomName)
                val students = arrayListOf<Student>()

                val sheet = workbook.getSheet(classroomName)
                for ((index, row) in sheet.withIndex()) {
                    if (index == 0) continue

                    var student = Student(nis = "", name = "", classroomId = 0L)
                    for ((cellIndex, cell) in row.withIndex()) {
                        val value = when (cell.cellType) {
                            CellType.NUMERIC -> {
                                DataFormatter().formatCellValue(cell)
                            }

                            CellType.STRING -> cell.stringCellValue
                            else -> cell.toString()
                        }
                        when (cellIndex) {
                            0 -> student = student.copy(nis = value)
                            1 -> student = student.copy(name = value)
                        }
                    }
                    students.add(student)
                }

                val classroomId = classroomDao.insert(classroom)
                studentDao.insertAll(students.map { it.copy(classroomId = classroomId) })
            }

            withContext(Dispatchers.IO) {
                inputStream.close()
            }
        }
    }
}