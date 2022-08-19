package com.d34th.nullpointer.dogedex.presentation

import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.domain.ia.RecognitionRepository
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    private val recognitionRepository: RecognitionRepository
) : ViewModel() {

    private val _messageCamera = Channel<String>()
    val messageCamera = _messageCamera.receiveAsFlow()

    var recognitionDog by mutableStateOf(false)
        private set

    private var currentIdMlDog by mutableStateOf("")
    val isPhotoReady get() = currentIdMlDog.isNotEmpty()

    private var editIdEnable by mutableStateOf(true)


    val isFirstRequestCamera = dogsRepository
        .isFirstCameraRequest().flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )

    fun changeRequestCamera(
        isRequestCamera: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        dogsRepository.changeIsFirstRequestCamera(isRequestCamera)
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


    fun getRecognizeDogSaved(callbackSuccess: (Dog, isNewDog: Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            recognitionDog = true
            when (val response = dogsRepository.getRecognizeDog(idRecognizeDog = currentIdMlDog)) {
                is ApiResponse.Failure -> _messageCamera.trySend(response.message)
                is ApiResponse.Success -> {
                    // * update status dog
                    dogsRepository.addDog(response.response)
                    val isNewDog = dogsRepository.isNewDog(response.response.name)
                    withContext(Dispatchers.Main) {
                        callbackSuccess(response.response, isNewDog)
                    }
                }
            }
            recognitionDog = false
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
            changeReadyPhoto(if (isConfidence) dog.id else "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        recognitionRepository.clearCamera()
    }
}