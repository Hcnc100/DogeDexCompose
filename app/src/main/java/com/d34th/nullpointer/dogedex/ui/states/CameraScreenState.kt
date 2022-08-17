package com.d34th.nullpointer.dogedex.ui.states

import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.d34th.nullpointer.dogedex.R
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    var lensFacing: Int,
    val imageCapture: ImageCapture
) : SimpleScreenState(scaffoldState, context) {

    private lateinit var executor: ExecutorService

    fun switchCamera() {
        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK)
            CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
    }

    private fun getOutputDirectory(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(
                it,
                context.resources.getString(R.string.app_name) + "${System.currentTimeMillis()}.jpgq"
            ).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    fun captureImage(
        OnSuccess: (Uri) -> Unit,
        OnError: () -> Unit
    ) {
        executor = Executors.newSingleThreadExecutor()
        val photoFile = getOutputDirectory()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    OnSuccess(savedUri)
                }

                override fun onError(exception: ImageCaptureException) {
                    OnError()
                }

            })
    }

    fun clearCamera() {
        if (::executor.isInitialized) executor.shutdown()
    }

}

@Composable
fun rememberCameraScreenState(
    context: Context = LocalContext.current,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    imageCapture: ImageCapture = ImageCapture.Builder().build(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) = remember {
    CameraScreenState(context, scaffoldState, lensFacing, imageCapture)

}