package com.d34th.nullpointer.dogedex.ui.screen.camera.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dogsRepository: DogsRepository
) : ViewModel() {

    val isFirstRequestCamera = dogsRepository
        .isFirstCameraRequest().flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )

    fun changeRequestCamera(isRequestCamera: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dogsRepository.changeIsFirstRequestCamera(isRequestCamera)
    }
}