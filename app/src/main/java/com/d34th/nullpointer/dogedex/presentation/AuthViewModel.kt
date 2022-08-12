package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.delegate.SavableComposeState
import com.d34th.nullpointer.dogedex.core.states.AuthState
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

    var isAuthenticating by SavableComposeState(savedStateHandle, KEY_IS_AUTH, false)
        private set

    val stateUser = flow {
        authRepo.currentUser.collect { currentUser ->
            if (currentUser.isAuth)
                emit(AuthState.Authenticated(currentUser))
            else
                emit(AuthState.Unauthenticated)
        }
    }.flowOn(Dispatchers.IO).catch {
        Timber.d("Error get user from prefereneces $it")
        emit(AuthState.Unauthenticated)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AuthState.Authenticating
    )

    fun signIn(
        userFieldsSignIn: UserFieldSignIn
    ) = viewModelScope.launch(Dispatchers.IO) {
        isAuthenticating = true
        when (val userResponse = authRepo.signIn(userFieldsSignIn)) {
            is ApiResponse.Failure -> _messageAuth.trySend(userResponse.message)
            else -> Unit
        }
        isAuthenticating = false
    }

    fun signUp(
        userFieldSignUp: UserFieldSignUp
    ) = viewModelScope.launch(Dispatchers.IO) {
        isAuthenticating = true
        when (val userResponse = authRepo.signUp(userFieldSignUp)) {
            is ApiResponse.Failure -> _messageAuth.trySend(userResponse.message)
            else -> Unit
        }
        isAuthenticating = false
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        authRepo.signOut()
    }

}