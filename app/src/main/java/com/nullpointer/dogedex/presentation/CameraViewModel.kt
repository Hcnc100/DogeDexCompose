package com.nullpointer.dogedex.presentation

import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.dogedex.core.utils.Constants.PERCENTAGE_CONFIDENCE
import com.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.nullpointer.dogedex.core.utils.launchSafeIO
import com.nullpointer.dogedex.domain.dogs.DogsRepository
import com.nullpointer.dogedex.domain.ia.RecognitionRepository
import com.nullpointer.dogedex.ia.DogRecognition
import com.nullpointer.dogedex.models.dogs.data.DogData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    private val recognitionRepository: RecognitionRepository
) : ViewModel() {

    private val _messageCamera = Channel<Int>()
    val messageCamera = _messageCamera.receiveAsFlow()

    var recognitionDog by mutableStateOf(false)
        private set

    private val currentMlDogId = MutableStateFlow<DogRecognition?>(null)
    val isReadyTakePhoto = currentMlDogId.map { it != null }

    val isFirstRequestCamera =
        dogsRepository.isFirstRequestCameraPermission.flowOn(Dispatchers.IO).catch {
            _messageCamera.trySend(showMessageForException(it, "get fist pref camera"))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )


    fun changeRequestCamera() = launchSafeIO {
        dogsRepository.changeIsFirstRequestCamera()
    }


    fun bindAnalyzeImage(
        cameraController: LifecycleCameraController,
    ) {
        recognitionRepository.bindAnalyzeImage(
            cameraController,
            callbackRecognizeDog = { dog: DogRecognition? ->
                Timber.d("Dog Recognize: $dog")
                currentMlDogId.value = when {
                    (dog?.confidence ?: 0f) > PERCENTAGE_CONFIDENCE -> dog
                    else -> null
                }
                // * add delay for not recognize the same dog
                // * and for can the user take a photo if the model change fast

                // ! can user delay 'cause internal this method is called in a new thread
                // ! this no block the main thread (Don't worry)
                sleep(2_000)
            }
        )
    }


    fun startRecognition(
        callbackSuccess: (dogData: DogData) -> Unit
    ) {
        currentMlDogId.value?.let { dogRecognition ->
            launchSafeIO(
                blockBefore = { recognitionDog = true },
                blockAfter = { recognitionDog = false },
                blockException = {
                    _messageCamera.trySend(showMessageForException(it, "recognize dog"))
                },
                blockIO = {
                    val dogFound = dogsRepository.getRecognizeDog(dogRecognition)
                    withContext(Dispatchers.Main) {
                        callbackSuccess(dogFound)
                    }
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        recognitionRepository.clearCamera()
    }
}