package com.nullpointer.dogedex.ui.share

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.ui.preview.config.SimplePreview
import com.nullpointer.dogedex.ui.preview.provider.BooleanProvider

@Composable
fun ProcessingActionButton(
    isReady: Boolean,
    onClick: () -> Unit,
    isProcessing: Boolean,
    modifier: Modifier = Modifier,
) {
    // * show button with alpha 0.3 when is not ready or is processing
    val currentAlpha by remember(isReady, isProcessing) {
        derivedStateOf { if (isReady || isProcessing) 1.0f else 0.3f }
    }

    // * show elevation when is ready
    val elevationButton = when {
        isReady -> FloatingActionButtonDefaults.elevation()
        else -> FloatingActionButtonDefaults.elevation(0.dp)
    }

    FloatingActionButton(
        elevation = elevationButton,
        modifier = modifier.size(56.dp),
        onClick = { if (!isProcessing && isReady) onClick() },
        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = currentAlpha)
    ) {
        when (isProcessing) {
            true -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            )

            false -> Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = stringResource(R.string.description_button_camera),
            )
        }
    }
}

@SimplePreview
@Composable
fun ProcessingActionButtonProcessingPreview(
    @PreviewParameter(BooleanProvider::class) isReady: Boolean,
) {
    ProcessingActionButton(
        onClick = {},
        isReady = isReady,
        isProcessing = true
    )
}

@SimplePreview
@Composable
fun ProcessingActionButtonNotProcessingPreview(
    @PreviewParameter(BooleanProvider::class) isReady: Boolean,
) {
    ProcessingActionButton(
        onClick = {},
        isReady = isReady,
        isProcessing = false
    )
}