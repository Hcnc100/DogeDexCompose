package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.delegate.SavableComposeState
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.d34th.nullpointer.dogedex.core.utils.launchSafeIO
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_LOAD_MY_DOG = "KEY_LOAD_MY_DOGS"
    }

    private val _messageDogs = Channel<Int>()
    val messageDogs get() = _messageDogs.receiveAsFlow()

    var isLoadingMyGogs by SavableComposeState(savedStateHandle, KEY_LOAD_MY_DOG, false)
        private set

    init {
        requestMyLastDogs()
    }

    val stateListDogs = flow<Resource<List<Dog>>> {
        dogsRepository.listDogs.collect {
            emit(Resource.Success(it))
        }
    }.catch {
        // ! only work this, for control the message error after
        emit(Resource.Failure)
        _messageDogs.trySend(showMessageForException(it, "get my dogs"))
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )

    fun requestMyLastDogs() = launchSafeIO(
        blockBefore = { isLoadingMyGogs = true },
        blockAfter = { isLoadingMyGogs = false },
        blockException = {
            _messageDogs.trySend(showMessageForException(it, "requestMyDogs"))
        },
        blockIO = { dogsRepository.refreshMyDogs() }
    )

    fun addDog(
        dog: Dog,
        callbackSuccess: () -> Unit
    ) = launchSafeIO(
        blockException = {
            _messageDogs.trySend(showMessageForException(it, "addDog"))
        },
        blockIO = {
            dogsRepository.addDog(dog)
            delay(1_000)
            withContext(Dispatchers.Main) {
                callbackSuccess()
            }
        }
    )
}