package com.d34th.nullpointer.dogedex.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.d34th.nullpointer.dogedex.core.utils.launchSafeIO
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
) : ViewModel() {


    private val _messageDogs = Channel<Int>()
    val messageDogs = _messageDogs.receiveAsFlow()

    var isLoadingMyGogs by mutableStateOf(false)
        private set

    val stateListDogs = dogsRepository.listDogs.map<List<DogData>, Resource<List<DogData>>> {
        Resource.Success(it)
    }.catch {
        emit(Resource.Failure)
        _messageDogs.trySend(showMessageForException(it, "get my dogs"))
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )


    init {
        requestMyLastDogs()
    }

    fun requestMyLastDogs() = launchSafeIO(
        blockBefore = { isLoadingMyGogs = true },
        blockAfter = { isLoadingMyGogs = false },
        blockException = {
            _messageDogs.trySend(showMessageForException(it, "requestMyDogs"))
        },
        blockIO = { dogsRepository.refreshMyDogs() }
    )

    fun addDog(
        dogData: DogData,
        callbackSuccess: () -> Unit
    ) = launchSafeIO(
        blockException = {
            _messageDogs.trySend(showMessageForException(it, "addDog"))
        },
        blockIO = {
            dogsRepository.addDog(dogData)
            _messageDogs.trySend(R.string.message_dog_saved)
            withContext(Dispatchers.Main) {
                callbackSuccess()
            }
        }
    )
}