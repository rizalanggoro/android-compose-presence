@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.classroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizalanggoro.presence.core.UiStateStatus
import com.rizalanggoro.presence.data.entities.Classroom
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomUiState.Action
import kotlinx.serialization.Serializable

@Serializable
data object ClassroomRoute

@Composable
fun ClassroomScreen(
    viewModel: ClassroomViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val classrooms by viewModel.classrooms.observeAsState()
    val uiState = viewModel.uiState

    var selectedClassroom by remember {
        mutableStateOf<Classroom?>(null)
    }
    var isUpsertClassroomVisible by remember {
        mutableStateOf(false)
    }
    var isDeleteClassroomVisible by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(uiState) {
        with(uiState) {
            when (action) {
                Action.Create -> {
                    if (status == UiStateStatus.Success) {
                        isUpsertClassroomVisible = false
                        snackbarHostState.showSnackbar(message = "Kelas baru ditambahkan!")
                    }
                }

                Action.Update -> {
                    if (status == UiStateStatus.Success) {
                        selectedClassroom = null
                        isUpsertClassroomVisible = false
                        snackbarHostState.showSnackbar(message = "Kelas berhasil diubah!")
                    }
                }

                Action.Delete -> {
                    if (status == UiStateStatus.Success) {
                        selectedClassroom = null
                        isDeleteClassroomVisible = false
                        snackbarHostState.showSnackbar(message = "Kelas berhasil dihapus!")
                    }
                }

                else -> {}
            }

            // reset state if status == success
            if (status == UiStateStatus.Success)
                viewModel.resetUiState()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Kelas")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "icon-button-back"
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.resetUiState()
                isUpsertClassroomVisible = true
            }) {
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
                        headlineContent = { Text(classroom.name) },
                        trailingContent = {
                            Row {
                                IconButton(onClick = {
                                    viewModel.resetUiState()
                                    selectedClassroom = classroom
                                    isUpsertClassroomVisible = true
                                }) {
                                    Icon(Icons.Rounded.Edit, contentDescription = "icon-edit")
                                }
                                IconButton(onClick = {
                                    selectedClassroom = classroom
                                    isDeleteClassroomVisible = true
                                }) {
                                    Icon(Icons.Rounded.Delete, contentDescription = "icon-delete")
                                }
                            }
                        }
                    )
                }
            }
        }

        // alert dialog create classroom
        if (isUpsertClassroomVisible)
            AlertDialogUpsertClassroom(
                classroom = selectedClassroom,
                onDismiss = { isUpsertClassroomVisible = false },
                onConfirm = { newName ->
                    if (selectedClassroom != null)
                        viewModel.update(
                            selectedClassroom!!.copy(
                                name = newName,
                            )
                        )
                    else
                        viewModel.create(newName)
                },
                isError = arrayOf(
                    Action.Create,
                    Action.Update
                ).contains(uiState.action) && uiState.status == UiStateStatus.Failure,
                errorMessage = uiState.message,
            )

        // alert dialog confirm delete
        if (isDeleteClassroomVisible)
            AlertDialogConfirmDelete(
                onDismiss = { isDeleteClassroomVisible = false },
                onConfirm = {
                    viewModel.delete(selectedClassroom!!)
                    isDeleteClassroomVisible = false
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

@Composable
private fun AlertDialogUpsertClassroom(
    classroom: Classroom? = null,
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
) {
    var name by remember {
        mutableStateOf(classroom?.name ?: "")
    }

    AlertDialog(
        title = { Text(text = if (classroom != null) "Ubah Kelas" else "Tambah Kelas") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Masukkan nama kelas") },
                isError = isError,
                supportingText = {
                    if (isError && errorMessage.isNotEmpty())
                        Text(text = errorMessage)
                }
            )
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name) }) {
                Text(text = "Simpan")
            }
        },
    )
}