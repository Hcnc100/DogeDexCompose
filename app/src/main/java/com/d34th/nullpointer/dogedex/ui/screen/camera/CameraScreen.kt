package com.d34th.nullpointer.dogedex.ui.screen.camera

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ui.screen.camera.viewModel.CameraViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.ListDogsScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SettingsScreenDestination
import com.d34th.nullpointer.dogedex.ui.states.ActionUiCamera
import com.d34th.nullpointer.dogedex.ui.states.ActionUiCamera.*
import com.d34th.nullpointer.dogedex.ui.states.CameraScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberCameraScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun CameraScreen(
    navigator: DestinationsNavigator,
    cameraViewModel: CameraViewModel = hiltViewModel(),
    cameraScreenState: CameraScreenState = rememberCameraScreenState()
) {
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    val isFirstRequestCamera by cameraViewModel.isFirstRequestCamera.collectAsState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            MainButtons(
                isEnableCamera = cameraPermissionState.status is PermissionStatus.Granted,
                actionIUCamera = { action ->
                    when (action) {
                        TAKE_PHOTO -> cameraScreenState.captureImage(OnSuccess = {
                            Timber.d("Success $it")
                        }, OnError = {})
                        OPEN_COLLECTION -> navigator.navigate(ListDogsScreenDestination)
                        OPEN_SETTINGS -> navigator.navigate(SettingsScreenDestination)
                    }
                }
            )
        }
    ) {
        when (cameraPermissionState.status) {
            PermissionStatus.Granted -> {
                CameraPreview(
                    modifier = Modifier.padding(it),
                    imageCapture = cameraScreenState.imageCapture
                )
            }
            is PermissionStatus.Denied -> {
                MessageCamera(
                    isFirstRequest = isFirstRequestCamera,
                    changeFirstRequest = cameraViewModel::changeRequestCamera,
                    modifier = Modifier.padding(it),
                    launchPermission = cameraPermissionState::launchPermissionRequest
                )
            }
        }
    }
}


@Composable
private fun MainButtons(
    modifier: Modifier = Modifier,
    isEnableCamera: Boolean,
    actionIUCamera: (ActionUiCamera) -> Unit
) {
    Row(modifier = modifier) {
        FloatingActionButton(onClick = { actionIUCamera(OPEN_COLLECTION) }) {
            Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "")
        }
        Spacer(modifier = Modifier.width(20.dp))
        if (isEnableCamera) {
            FloatingActionButton(onClick = { actionIUCamera(TAKE_PHOTO) }) {
                Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "")
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        FloatingActionButton(onClick = { actionIUCamera(OPEN_SETTINGS) }) {
            Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        }
    }
}