package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val _messageDogs = Channel<String>()
    val messageDogs get() = _messageDogs.receiveAsFlow()

    val stateListDogs = flow<Resource<List<Dog>>> {
        dogsRepository.getDogs().collect {
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
}