package com.rizalanggoro.presence.ui.screens.classroom

import com.rizalanggoro.presence.core.UiStateStatus

data class ClassroomUiState(
    val action: Action = Action.Initial,
    val status: UiStateStatus = UiStateStatus.Initial,
    val message: String = "",
) {
    enum class Action {
        Initial,
        Create,
        Update,
        Delete,
    }
}