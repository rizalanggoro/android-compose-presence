@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizalanggoro.presence.ui.screens.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.rizalanggoro.presence.R
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

@Composable
fun SettingScreen(
    onNavigateBack: () -> Unit,
    onNavigateToClassroom: () -> Unit,
    onNavigateToImportConfig: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Setelan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "icon-button-back"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ListItem(
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_class_24),
                        contentDescription = "icon-classroom"
                    )
                },
                trailingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_chevron_right_24),
                        contentDescription = "icon-classroom"
                    )
                },
                modifier = Modifier.clickable { onNavigateToClassroom() },
                headlineContent = { Text(text = "Kelas") },
                supportingContent = { Text("Tambah, ubah, atau hapus kelas") },
            )
            ListItem(
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_file_download_24),
                        contentDescription = "icon-classroom"
                    )
                },
                trailingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_chevron_right_24),
                        contentDescription = "icon-classroom"
                    )
                },
                modifier = Modifier.clickable { onNavigateToImportConfig() },
                headlineContent = { Text(text = "Impor") },
                supportingContent = { Text("Tambah, ubah, atau hapus kelas") },
            )
        }
    }
}
