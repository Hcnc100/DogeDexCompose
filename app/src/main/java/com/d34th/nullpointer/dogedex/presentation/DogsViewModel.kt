package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.domain.DogsRepository
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val _messageDogs = Channel<String>()
    val messageDogs get() = _messageDogs.receiveAsFlow()

    val stateListDogs = flow {
        when (val result = dogsRepository.getAllDogs()) {
            is ApiResponse.Failure -> {
                _messageDogs.trySend(result.message)
                emit(Resource.Failure)
            }
            is ApiResponse.Success -> {
                emit(Resource.Success(result.response))
            }
        }
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )

    fun addDog(dog: Dog) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = dogsRepository.addDog(dog)) {
            is ApiResponse.Failure -> _messageDogs.trySend(response.message)
            is ApiResponse.Success -> _messageDogs.trySend("Success")
        }
    }
}