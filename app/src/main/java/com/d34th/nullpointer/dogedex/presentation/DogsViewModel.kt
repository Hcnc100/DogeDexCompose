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
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val _messageDogs = Channel<String>()
    val messageDogs get() = _messageDogs.receiveAsFlow()

    val stateListDogs = flow {
        when(val result=dogsRepository.getDogs()){
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
}