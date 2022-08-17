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
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DirectionDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SettingsScreenDestination
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberSimpleScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun CameraScreen(
    navigator: DestinationsNavigator,
    cameraViewModel: CameraViewModel = hiltViewModel(),
    cameraScreenState: SimpleScreenState = rememberSimpleScreenState(),
) {
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    val isFirstRequestCamera by cameraViewModel.isFirstRequestCamera.collectAsState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            MainButtons(
                changeDestination = navigator::navigate,
                isEnableCamera = cameraPermissionState.status is PermissionStatus.Granted
            )
        }
    ) {
        when (cameraPermissionState.status) {
            PermissionStatus.Granted -> {
                CameraPreview(modifier = Modifier.padding(it))
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
    changeDestination: (DirectionDestination) -> Unit
) {
    Row(modifier = modifier) {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "")
        }
        Spacer(modifier = Modifier.width(20.dp))
        if (isEnableCamera) {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "")
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        FloatingActionButton(onClick = { changeDestination(SettingsScreenDestination) }) {
            Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        }
    }
}