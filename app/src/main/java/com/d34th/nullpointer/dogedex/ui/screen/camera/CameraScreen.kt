package com.d34th.nullpointer.dogedex.ui.screen.camera

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.presentation.CameraViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DogDetailsDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.ListDogsScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SettingsScreenDestination
import com.d34th.nullpointer.dogedex.ui.share.ProcessingActionButton
import com.d34th.nullpointer.dogedex.ui.states.ActionUiCamera
import com.d34th.nullpointer.dogedex.ui.states.ActionUiCamera.*
import com.d34th.nullpointer.dogedex.ui.states.CameraScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberCameraScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun CameraScreen(
    navigator: DestinationsNavigator,
    cameraViewModel: CameraViewModel = hiltViewModel(),
    cameraScreenState: CameraScreenState = rememberCameraScreenState()
) {
    val isFirstRequestCamera by cameraViewModel.isFirstRequestCamera.collectAsState()

    LaunchedEffect(key1 = Unit) {
        cameraViewModel.messageCamera.collect(cameraScreenState::showSnackMessage)
    }

    // * clear task to process camera
    DisposableEffect(key1 = Unit) {
        onDispose(cameraScreenState::clearCamera)
    }

    Scaffold(
        scaffoldState = cameraScreenState.scaffoldState,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            MainButtons(
                isRecognizing = cameraViewModel.recognitionDog,
                isEnableCamera = cameraScreenState.isCameraPermissionGranted,
                isReadingPhoto = cameraViewModel.isPhotoReady,
                actionIUCamera = { action ->
                    when (action) {
                        TAKE_PHOTO -> {
                            cameraViewModel.getRecognizeDogSaved { dog ->
                                navigator.navigate(DogDetailsDestination(dog))
                            }
                        }
                        OPEN_COLLECTION -> navigator.navigate(ListDogsScreenDestination)
                        OPEN_SETTINGS -> navigator.navigate(SettingsScreenDestination)
                    }
                }
            )
        }
    ) { paddingValues ->
        when (cameraScreenState.cameraPermissionStatus) {
            PermissionStatus.Granted -> {
                CameraPreview(
                    modifier = Modifier.padding(paddingValues),
                    bindCameraToUseCases = {
                        cameraScreenState.bindCameraToUseCases(it) { dogRecognition: DogRecognition ->
                            val idMlDog =
                                if (dogRecognition.confidence > 70f) dogRecognition.id else ""
                            cameraViewModel.changeReadyPhoto(idMlDog)
                        }
                    },
                )
            }
            is PermissionStatus.Denied -> {
                MessageCamera(
                    isFirstRequest = isFirstRequestCamera,
                    changeFirstRequest = cameraViewModel::changeRequestCamera,
                    modifier = Modifier.padding(paddingValues),
                    launchPermission = cameraScreenState::launchPermissionCamera
                )
            }
        }
    }
}


@Composable
private fun MainButtons(
    modifier: Modifier = Modifier,
    isEnableCamera: Boolean,
    isRecognizing: Boolean,
    isReadingPhoto: Boolean,
    actionIUCamera: (ActionUiCamera) -> Unit
) {
    Row(modifier = modifier) {
        FloatingActionButton(onClick = { actionIUCamera(OPEN_COLLECTION) }) {
            Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "")
        }
        Spacer(modifier = Modifier.width(20.dp))
        if (isEnableCamera) {
            ProcessingActionButton(
                isProcessing = isRecognizing,
                onClick = { actionIUCamera(TAKE_PHOTO) },
                contentDescription = "",
                painter = painterResource(id = R.drawable.ic_camera),
                isReady = isReadingPhoto
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        FloatingActionButton(onClick = { actionIUCamera(OPEN_SETTINGS) }) {
            Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        }
    }
}