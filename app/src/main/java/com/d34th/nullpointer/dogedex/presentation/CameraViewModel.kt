package com.d34th.nullpointer.dogedex.presentation

import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.d34th.nullpointer.dogedex.core.utils.launchSafeIO
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.domain.ia.RecognitionRepository
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    private var currentIdMlDog by mutableStateOf("")

    val isPhotoReady get() = currentIdMlDog.isNotEmpty()

    private var editIdEnable = true

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

    private fun changeReadyPhoto(dogId: String) = viewModelScope.launch {
        // * has delay for no remove id dog selected fasted
        if (editIdEnable && dogId != currentIdMlDog) {
            editIdEnable = false
            currentIdMlDog = dogId
            // * delay move to IO thread
            withContext(Dispatchers.IO) { delay(2_000) }
            editIdEnable = true
        }
    }


    fun initRecognition(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) = viewModelScope.launch {
        recognitionRepository.bindCameraToUseCases(
            previewView = previewView,
            lifecycleOwner = lifecycleOwner
        ) { dog: DogRecognition, isConfidence: Boolean ->
            Timber.d("${dog.confidence}")
            changeReadyPhoto(if (isConfidence) dog.id else "")
        }
    }

    fun getRecognizeDogSaved(
        callbackSuccess: (Dog, isNewDog: Boolean) -> Unit
    ) = launchSafeIO(
        blockBefore = { recognitionDog = true },
        blockAfter = { recognitionDog = false },
        blockException = {
            _messageCamera.trySend(showMessageForException(it, "recognize dog"))
        },
        blockIO = {
            val dogRecognition = dogsRepository.getRecognizeDog(idRecognizeDog = currentIdMlDog)
            val isNewDog = dogsRepository.isNewDog(dogRecognition.name)
            withContext(Dispatchers.Main) {
                callbackSuccess(dogRecognition, isNewDog)
            }
        }
    )

    override fun onCleared() {
        super.onCleared()
        recognitionRepository.clearCamera()
    }
}