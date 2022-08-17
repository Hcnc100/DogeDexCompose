package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.delegate.SavableComposeState
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageDogs = Channel<String>()
    val messageDogs get() = _messageDogs.receiveAsFlow()

    var isLoadingMyGogs by SavableComposeState(savedStateHandle, "KEY_LOAD_MY_DOGS", false)
        private set

    init {
        requestMyLastDogs()
    }

    val stateListDogs = flow<Resource<List<Dog>>> {
        dogsRepository.getAllDogs().collect {
            emit(Resource.Success(it))
        }
    }.catch {
        // ! only work this, for control the message error after
        emit(Resource.Failure)
        it.message?.let { message -> _messageDogs.trySend(message) }
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )

    fun requestMyLastDogs() = viewModelScope.launch {
        try {
            isLoadingMyGogs = true
            withContext(Dispatchers.IO) {
                dogsRepository.refreshMyDogs()
            }
        } catch (e: Exception) {
            Timber.e("Error load my dogs")
        } finally {
            isLoadingMyGogs = false
        }

    }

    fun addDog(dog: Dog) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = dogsRepository.addDog(dog)) {
            is ApiResponse.Failure -> _messageDogs.trySend(response.message)
            is ApiResponse.Success -> _messageDogs.trySend("Success")
        }
    }
}