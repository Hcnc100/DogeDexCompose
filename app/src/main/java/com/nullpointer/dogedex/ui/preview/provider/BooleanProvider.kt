package com.nullpointer.dogedex.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanProvider : PreviewParameterProvider<Boolean> {
    override val values = listOf(true, false).asSequence()
}