@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.classroom

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizalanggoro.presence.data.entities.Classroom

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClassroomScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (id: Int) -> Unit,
) {
    val viewModel = hiltViewModel<ClassroomViewModel>()
    val classrooms by viewModel.classrooms.observeAsState()

    var classroomForDelete by remember {
        mutableStateOf<Classroom?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Kelas")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "icon-button-back"
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Rounded.Add, contentDescription = "icon-add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(classrooms?.size ?: 0) { index ->
                    val classroom = classrooms!![index]
                    ListItem(
                        modifier = Modifier.combinedClickable(
                            onClick = { onNavigateToDetail(classroom.id) },
                            onLongClick = { classroomForDelete = classroom },
                        ),
                        headlineContent = { Text(classroom.name) },
                    )
                }
            }
        }

        // alert dialog confirm delete
        if (classroomForDelete != null)
            AlertDialogConfirmDelete(
                onDismiss = { classroomForDelete = null },
                onConfirm = {
                    viewModel.deleteClassroom(classroomForDelete!!)
                    classroomForDelete = null
                }
            )
    }
}

@Composable
private fun AlertDialogConfirmDelete(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = "Hapus") },
        text = { Text(text = "Apakah anda yakin ingin menghapus kelas ini?") },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Hapus")
            }
        },
    )
}