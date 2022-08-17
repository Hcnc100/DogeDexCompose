package com.d34th.nullpointer.dogedex.ui.screen.camera

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.d34th.nullpointer.dogedex.core.utils.executor
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    imageCapture: ImageCapture
) {

    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    Box {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }


                // CameraX Preview UseCase
                val previewUseCase = Preview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                coroutineScope.launch {
                    val cameraProvider = context.getCameraProvider()
                    try {
                        // Must unbind the use-cases before rebinding them.
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner, cameraSelector, previewUseCase, imageCapture
                        )
                    } catch (ex: Exception) {
                        Timber.d("Use case binding failed $ex")
                    }
                }

                previewView
            },
        )
    }

}

fun takePicture(context: Context, view: View) {
    val fileOutput = getTmpFile(context)
    val imageCapture = ImageCapture.Builder().setTargetRotation(view.display.rotation).build()
    val executor = Executors.newSingleThreadExecutor()
    val outputFile = ImageCapture.OutputFileOptions.Builder(fileOutput).build()
    imageCapture.takePicture(outputFile, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Timber.d("Success $outputFile")
        }

        override fun onError(exception: ImageCaptureException) {
            Timber.d("Error take photo")
        }

    })
}


private fun getTmpFile(context: Context): File {
    val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
        createNewFile()
        deleteOnExit()
    }
    return tmpFile
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { future ->
            future.addListener({
                continuation.resume(future.get())
            }, executor)
        }
    }
