package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.delegate.SavableComposeState
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignIn
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepo: AuthRepository
) : ViewModel() {

    companion object {
        private const val KEY_IS_AUTH = "KEY_IS_AUTH"
    }

    private val _messageAuth = Channel<String>()
    val messageAuth = _messageAuth.receiveAsFlow()

    var isAuthenticated by SavableComposeState(savedStateHandle, KEY_IS_AUTH, false)
        private set

    val isAuthUser = flow<Resource<Boolean>> {
        authRepo.isAuthUser.collect {
            emit(Resource.Success(it))
        }
    }.catch {
        Timber.d("Error get user from prefereneces $it")
        emit(Resource.Failure)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
    )

    fun signIn(
        userFieldsSignIn: UserFieldSignIn
    ) = viewModelScope.launch(Dispatchers.IO) {
        isAuthenticated = true
        when (val userResponse = authRepo.signIn(userFieldsSignIn)) {
            is ApiResponse.Failure -> _messageAuth.trySend(userResponse.message)
            else -> Unit
        }
        isAuthenticated = false
    }

    fun signUp(
        userFieldSignUp: UserFieldSignUp
    ) = viewModelScope.launch {
        isAuthenticated = true
        when (val userResponse = authRepo.signUp(userFieldSignUp)) {
            is ApiResponse.Failure -> _messageAuth.trySend(userResponse.message)
            else -> Unit
        }
        isAuthenticated = false
    }

}