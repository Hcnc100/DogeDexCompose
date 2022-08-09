package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    state:SavedStateHandle
) : ViewModel() {

    private val _messageDogs = Channel<String>()
    val messageDogs get() = _messageDogs.receiveAsFlow()

    val listFakeDogs = listOf(
        Dog(
            1, 1, "Chihuahua", "Toy", 5.4,
            6.7, "", "12 - 15", "", 10.5,
            12.3
        ),
        Dog(
            2, 1, "Labrador", "Toy", 5.4,
            6.7, "", "12 - 15", "", 10.5,
            12.3

        ),
        Dog(
            3, 1, "Retriever", "Toy", 5.4,
            6.7, "", "12 - 15", "", 10.5,
            12.3
        ),
        Dog(
            4, 1, "San Bernardo", "Toy", 5.4,
            6.7, "", "12 - 15", "", 10.5,
            12.3
        ),
        Dog(
            5, 1, "Husky", "Toy", 5.4,
            6.7, "", "12 - 15", "", 10.5,
            12.3
        ),
        Dog(
            6, 1, "Xoloscuincle", "Toy", 5.4,
            6.7, "", "12 - 15", "", 10.5,
            12.3
        )

    )

    val stateListDogs = flow<Resource<List<Dog>>> {
        delay(5_000)
        emit(Resource.Success(listFakeDogs))
    }.catch {
        Timber.e("Error get dogs $it")
        emit(Resource.Failure)
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )
}