package com.d34th.nullpointer.dogedex.ui.states

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.d34th.nullpointer.dogedex.core.utils.getCameraProvider
import com.d34th.nullpointer.dogedex.core.utils.getExternalFile
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
class CameraScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    cameraSelector: CameraSelector,
    private val coroutineScope: CoroutineScope,
    private val lifecycleOwner: LifecycleOwner,
    private val cameraPermissionState: PermissionState
) : SimpleScreenState(scaffoldState, context) {

    private lateinit var executor: ExecutorService

    // * user case camera
    private val imageCapture: ImageCapture = ImageCapture.Builder().build()
    private val previewUseCase: Preview = Preview.Builder().build()
    private val currentCameraSelector = cameraSelector

    // * permissions
    val cameraPermissionStatus get() = cameraPermissionState.status
    val isCameraPermissionGranted get() = cameraPermissionState.status == PermissionStatus.Granted
    fun launchPermissionCamera() = cameraPermissionState.launchPermissionRequest()



    fun captureImage(
        OnSuccess: (Uri) -> Unit,
        OnError: (ImageCaptureException) -> Unit
    ) {
        executor = Executors.newSingleThreadExecutor()
        val photoFile = context.getExternalFile()
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
                    OnError(exception)
                }
            })
    }

    fun bindCameraToUseCases(previewView: PreviewView) {
        // * bind view preview to preview use case
        previewUseCase.setSurfaceProvider(previewView.surfaceProvider)
        coroutineScope.launch {
            val cameraProvider = context.getCameraProvider()
            try {
                // * Must unbind the use-cases before rebinding them.
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    currentCameraSelector,
                    previewUseCase,
                    imageCapture
                )
            } catch (ex: Exception) {
                Timber.d("Use case binding failed $ex")
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun clearCamera() {
        if (::executor.isInitialized) executor.shutdown()
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberCameraScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lensFacing: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    cameraPermissionState: PermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA),
    scope: CoroutineScope = rememberCoroutineScope(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) = remember(scaffoldState, cameraPermissionState, scope) {
    CameraScreenState(
        context = context,
        scaffoldState = scaffoldState,
        lifecycleOwner = lifecycleOwner,
        coroutineScope = scope,
        cameraSelector = lensFacing,
        cameraPermissionState = cameraPermissionState
    )
}