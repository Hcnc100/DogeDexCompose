package com.d34th.nullpointer.dogedex.ui.share

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ProcessingActionButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isProcessing: Boolean,
    isReady: Boolean
) {

    val currentAlpha by remember(isReady, isProcessing) {
        derivedStateOf { if (isReady || isProcessing) 1.0f else 0.3f }
    }


    FloatingActionButton(
        onClick = { if (!isProcessing && isReady) onClick() },
        modifier = modifier.size(56.dp),
        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = currentAlpha)
    ) {
        if (isProcessing)
            CircularProgressIndicator(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) else Icon(
            painter,
            contentDescription = contentDescription
        )
    }
}