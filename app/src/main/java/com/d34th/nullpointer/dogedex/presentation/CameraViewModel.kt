package com.d34th.nullpointer.dogedex.presentation

import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.d34th.nullpointer.dogedex.core.utils.launchSafeIO
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.domain.ia.RecognitionRepository
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
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

    private val currentIdMlDog2 = MutableStateFlow<DogRecognition?>(null)

    val isReadyTakePhoto2 = currentIdMlDog2.onEach {
        Timber.d("Model Recognize: $it")
    }.map { it != null }

    private var canChangeModel = true


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
        recognitionRepository.bindAnalyzeImage(cameraController) { dog: DogRecognition, isConfidence: Boolean ->
            if (!canChangeModel) return@bindAnalyzeImage
            viewModelScope.launch {
                canChangeModel = false
                currentIdMlDog2.value = when (isConfidence) {
                    true -> dog
                    false -> null
                }
                withContext(Dispatchers.IO) { delay(2_000) }
                canChangeModel = true
            }
        }
    }


    fun getRecognizeDogSaved(
        callbackSuccess: (dogData: DogData) -> Unit
    ) {
        this.currentIdMlDog2.value?.let { dogRecognition ->
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