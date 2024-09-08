@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@Composable
fun ModalDatePicker(
    initialDate: Long? = null,
    onDateChanged: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val state = rememberDatePickerState(
        initialDate ?: System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Picker,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDate = state.selectedDateMillis
                    if (selectedDate != null) {
                        onDateChanged(selectedDate)
                        onDismiss()
                    }
                }) {
                Text(text = "Selesai")
            }
        }) {
        DatePicker(
            state,
            showModeToggle = false
        )
    }
}