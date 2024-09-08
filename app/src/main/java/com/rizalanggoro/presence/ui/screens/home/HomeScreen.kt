@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rizalanggoro.presence.ui.components.ModalDatePicker
import com.rizalanggoro.presence.ui.screens.SettingRoute
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Presence")
                },
                actions = {
                    IconButton(
                        onClick = { isDatePickerVisible = true }) {
                        Icon(
                            Icons.Rounded.DateRange,
                            contentDescription = "button-change-date"
                        )
                    }
                    IconButton(onClick = { navController.navigate(SettingRoute) }) {
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
            // current date
            Text(
                text = SimpleDateFormat(
                    "EEEE, d MMM yyyy",
                    Locale("id")
                ).format(viewModel.currentDate)
            )

            // list classrooms
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

@Composable
private fun ListItemClassroom() {
}