package com.rizalanggoro.presence.ui.screens

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object SettingRoute

// classroom
@Serializable
data object ClassroomRoute

@Serializable
data object CreateClassroomRoute

@Serializable
data class DetailClassroomRoute(
    val id: Int,
)
