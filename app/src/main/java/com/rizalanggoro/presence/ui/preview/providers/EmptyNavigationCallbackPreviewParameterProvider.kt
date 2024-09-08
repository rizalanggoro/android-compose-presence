package com.rizalanggoro.presence.ui.preview.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class EmptyNavigationCallbackPreviewParameterProvider : PreviewParameterProvider<() -> Unit> {
    override val values: Sequence<() -> Unit> = sequenceOf({})
}
