package com.d34th.nullpointer.dogedex.ui.screen.camera.components

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    bindCameraToUseCases: (previewView: PreviewView) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // * create view to preview
            val previewView = PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            bindCameraToUseCases(previewView)
            previewView
        },
    )
}

@SimplePreview
@Composable
private fun CameraPreviewPreview() {
    CameraPreview(
        bindCameraToUseCases = {}
    )
}


