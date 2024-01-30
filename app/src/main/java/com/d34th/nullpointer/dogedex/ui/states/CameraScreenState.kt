package com.d34th.nullpointer.dogedex.ui.states

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
class CameraScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val lifecycleOwner: LifecycleOwner,
    private val cameraPermissionState: PermissionState
) : SimpleScreenState(scaffoldState, context) {

    // * permissions
    val cameraPermissionStatus get() = cameraPermissionState.status
    val isCameraPermissionGranted get() = cameraPermissionState.status == PermissionStatus.Granted
    fun launchPermissionCamera() = cameraPermissionState.launchPermissionRequest()

    fun openSettingsApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberCameraScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    cameraPermissionState: PermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
) = remember(scaffoldState, cameraPermissionState) {
    CameraScreenState(
        context = context,
        scaffoldState = scaffoldState,
        lifecycleOwner = lifecycleOwner,
        cameraPermissionState = cameraPermissionState
    )

}