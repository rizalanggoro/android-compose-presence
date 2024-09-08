@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.classroom.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizalanggoro.presence.core.UiStateStatus
import com.rizalanggoro.presence.ui.preview.providers.EmptyNavigationCallbackPreviewParameterProvider
import com.rizalanggoro.presence.ui.screens.classroom.create.CreateClassroomUiState.Action
import com.rizalanggoro.presence.ui.screens.classroom.create.CreateClassroomUiState.FailureType

@Preview(showBackground = true)
@Composable
fun CreateClassroomScreen(
    @PreviewParameter(EmptyNavigationCallbackPreviewParameterProvider::class)
    onNavigateBack: () -> Unit,
) {
    val viewModel = hiltViewModel<CreateClassroomViewModel>()
    val uiState = viewModel.uiState
    val isTextFieldNameError = uiState.action == Action.Create
            && uiState.failureType == FailureType.Name

    LaunchedEffect(uiState) {
        when (uiState.action) {
            Action.Create -> {
                if (uiState.status == UiStateStatus.Success) {
                    onNavigateBack()
                }
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Tambah Kelas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "icon-back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                placeholder = { Text("Masukkan nama kelas") },
                isError = isTextFieldNameError,
                supportingText = {
                    if (isTextFieldNameError) {
                        Text(text = uiState.message)
                    }
                }
            )
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp, end = 16.dp),
                onClick = { viewModel.createClassroom() }) {
                Text(text = "Simpan")
            }
        }
    }
}