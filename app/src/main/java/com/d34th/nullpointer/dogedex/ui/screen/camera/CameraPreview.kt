package com.d34th.nullpointer.dogedex.ui.screen.camera

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    bindCameraToUseCases: (previewView: PreviewView) -> Unit
) {
    Box {
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
}


