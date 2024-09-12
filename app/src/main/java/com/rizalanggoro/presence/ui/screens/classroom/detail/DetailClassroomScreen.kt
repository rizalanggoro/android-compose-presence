@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.classroom.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizalanggoro.presence.core.PresenceStatus
import com.rizalanggoro.presence.data.entities.Student
import com.rizalanggoro.presence.ui.components.ModalDatePicker
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class DetailClassroomRoute(
    val classroomId: Long,
)

@Composable
fun DetailClassroomScreen(
    viewModel: DetailClassroomViewModel = hiltViewModel(),
    classroomId: Long,
    onNavigateBack: () -> Unit,
) {
    val classroom by viewModel.classroom(classroomId).observeAsState()
    val students by viewModel.students(classroomId).observeAsState()
    val presences by viewModel.presences(classroomId).observeAsState()

    var isDatePickerVisible by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isSheetVisible by remember { mutableStateOf(false) }

    var selectedStudent by remember { mutableStateOf<Student?>(null) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = classroom?.name ?: "Detail Kelas") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "icon-back"
                    )
                }
            })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // current date
            OutlinedCard(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { isDatePickerVisible = true }
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            Icons.Rounded.DateRange,
                            contentDescription = "button-change-date",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Text(text = "Tanggal", fontWeight = FontWeight.SemiBold)
                        Text(
                            text = SimpleDateFormat(
                                "EEEE, d MMM yyyy",
                                Locale("id")
                            ).format(viewModel.currentDate)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // list student
                items(students?.size ?: 0) {
                    val student = students!![it]

                    ListItem(
                        trailingContent = {
                            Text(
                                text = presences?.find { presence ->
                                    presence.studentNis == student.nis
                                }?.status?.toString() ?: "-"
                            )
                        },
                        headlineContent = { Text(text = student.name) },
                        supportingContent = { Text(text = student.nis) },
                        modifier = Modifier.clickable {
                            isSheetVisible = true
                            selectedStudent = student
                        }
                    )
                }
            }

            // bottom sheet update presence status
            if (isSheetVisible)
                ModalBottomSheet(
                    onDismissRequest = {
                        isSheetVisible = false
                        selectedStudent = null
                    },
                    sheetState = sheetState,
                ) {
                    Column {
                        Text(
                            text = "Status Kehadiran",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        )
                        Text(
                            text = "Pilih salah satu status kehadiran siswa sebagai berikut ini",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp),
                        )
                        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                            items(
                                listOf(
                                    PresenceItem("Hadir", PresenceStatus.PRESENT),
                                    PresenceItem("Absen", PresenceStatus.ABSENT),
                                    PresenceItem("Izin", PresenceStatus.PERMISSION),
                                    PresenceItem("Sakit", PresenceStatus.SICK),
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            if (selectedStudent != null) {
                                                viewModel.upsertPresenceStatus(
                                                    studentNis = selectedStudent!!.nis,
                                                    status = it.status,
                                                )

                                                scope
                                                    .launch { sheetState.hide() }
                                                    .invokeOnCompletion {
                                                        if (!sheetState.isVisible) {
                                                            isSheetVisible = false
                                                            selectedStudent = null
                                                        }
                                                    }
                                            }
                                        }
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Spacer(modifier = Modifier.width(24.dp))
                                    RadioButton(
                                        selected = when (selectedStudent) {
                                            null -> false
                                            else -> presences?.find { presence ->
                                                presence.studentNis == selectedStudent!!.nis
                                            }?.status == it.status
                                        },
                                        onClick = null,
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = it.label,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                            }
                        }
                        OutlinedButton(
                            onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        isSheetVisible = false
                                        selectedStudent = null
                                    }
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(end = 16.dp, top = 8.dp, bottom = 16.dp)
                        ) {
                            Text(text = "Batal")
                        }
                    }
                }

            // date picker
            if (isDatePickerVisible)
                ModalDatePicker(
                    onDateChanged = { viewModel.updateDate(it) },
                    onDismiss = { isDatePickerVisible = false },
                    initialDate = viewModel.currentDate,
                )
        }
    }
}

private data class PresenceItem(
    val label: String,
    val status: PresenceStatus,
)