package com.d34th.nullpointer.dogedex.ui.screen.camera

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.navigation.HomeNavGraph
import com.d34th.nullpointer.dogedex.presentation.CameraViewModel
import com.d34th.nullpointer.dogedex.ui.screen.camera.CameraAction.*
import com.d34th.nullpointer.dogedex.ui.screen.camera.components.CameraPreview
import com.d34th.nullpointer.dogedex.ui.screen.camera.test.CameraTestTag
import com.d34th.nullpointer.dogedex.ui.share.ProcessingActionButton
import com.d34th.nullpointer.dogedex.ui.states.CameraScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberCameraScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalPermissionsApi::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    cameraScreenState: CameraScreenState = rememberCameraScreenState()
) {
    val isFirstRequestCamera by cameraViewModel.isFirstRequestCamera.collectAsState()
    val isPhotoReady by cameraViewModel.isReadyTakePhoto.collectAsState()

    LaunchedEffect(key1 = Unit) {
        cameraViewModel.messageCamera.collect(cameraScreenState::showSnackMessage)
    }

    CameraScreen(
        isFirstRequestCamera = isFirstRequestCamera,
        isPhotoReady = isPhotoReady,
        recognitionDog = cameraViewModel.recognitionDog,
        scaffoldState = cameraScreenState.scaffoldState,
        cameraPermissionStatus = cameraScreenState.cameraPermissionStatus,
        bindCameraToUseCases = {
            cameraViewModel.initRecognition(
                previewView = it,
                lifecycleOwner = cameraScreenState.lifecycleOwner
            )
        },
        onCameraAction = { action ->
            when (action) {
                INIT_RECOGNITION -> cameraViewModel.initRecognition(
                    previewView = PreviewView(cameraScreenState.context),
                    lifecycleOwner = cameraScreenState.lifecycleOwner
                )

                REQUEST_CAMERA_PERMISSION -> {
                    cameraScreenState.launchPermissionCamera()
                    cameraViewModel.changeRequestCamera()
                }

                OPEN_SETTINGS -> cameraScreenState.openSettingsApp()
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    isPhotoReady: Boolean,
    recognitionDog: Boolean,
    scaffoldState: ScaffoldState,
    isFirstRequestCamera: Boolean,
    onCameraAction: (CameraAction) -> Unit,
    cameraPermissionStatus: PermissionStatus,
    bindCameraToUseCases: (previewView: PreviewView) -> Unit
) {


    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ProcessingActionButton(
                isReady = isPhotoReady,
                isProcessing = recognitionDog,
                onClick = { onCameraAction(INIT_RECOGNITION) },
            )
        }
    ) { paddingValues ->
        when (cameraPermissionStatus) {
            PermissionStatus.Granted -> {
                CameraPreview(
                    modifier = Modifier
                        .padding(paddingValues)
                        .semantics { testTag = CameraTestTag.SCREEN_CAPTURE_IMAGE },
                    bindCameraToUseCases = bindCameraToUseCases,
                )
            }

            is PermissionStatus.Denied -> {
                PermissionsCameraScreen(
                    isFirstRequest = isFirstRequestCamera,
                    modifier = Modifier
                        .padding(paddingValues)
                        .semantics { testTag = CameraTestTag.SCREEN_PERMISSION },
                    launchPermission = { onCameraAction(REQUEST_CAMERA_PERMISSION) },
                    launchOpenSettings = { onCameraAction(OPEN_SETTINGS) }
                )
            }
        }
    }
}
