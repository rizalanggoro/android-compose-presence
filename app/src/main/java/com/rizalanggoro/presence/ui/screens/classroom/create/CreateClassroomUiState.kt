package com.rizalanggoro.presence.ui.screens.classroom.create

import com.rizalanggoro.presence.core.UiStateStatus

data class CreateClassroomUiState(
    val action: Action = Action.Initial,
    val status: UiStateStatus = UiStateStatus.Initial,

    val message: String = "",
    val failureType: FailureType = FailureType.Default,
) {
    enum class Action {
        Initial,
        Create,
    }

    enum class FailureType {
        Default,
        Name,
    }
}
