@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizalanggoro.presence.ui.components.ModalDatePicker
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data object HomeRoute

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSetting: () -> Unit,
    onNavigateToDetailClassroom: (classroomId: Long) -> Unit = {},
) {
    val classrooms by viewModel.classrooms.observeAsState()
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Presence")
                },
                actions = {
                    IconButton(onClick = onNavigateToSetting) {
                        Icon(
                            Icons.Rounded.Settings,
                            contentDescription = "button-setting"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
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
                }

                // list classrooms
                item {
                    Text(
                        text = "Daftar Kelas",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                items(classrooms?.size ?: 0) {
                    val classroom = classrooms!![it]

                    OutlinedCard(
                        onClick = { onNavigateToDetailClassroom(classroom.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = classroom.name)
                        }
                    }
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
