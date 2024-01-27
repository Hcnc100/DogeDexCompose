package com.d34th.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.delegate.SavableComposeState
import com.d34th.nullpointer.dogedex.core.states.AuthState
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.d34th.nullpointer.dogedex.core.utils.launchSafeIO
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import com.d34th.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.d34th.nullpointer.dogedex.models.auth.dto.SignUpDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepo: AuthRepository
) : ViewModel() {

    companion object {
        private const val KEY_IS_AUTH = "KEY_IS_AUTH"
    }

    private val _messageAuth = Channel<Int>()
    val messageAuth = _messageAuth.receiveAsFlow()

    var isAuthenticating by SavableComposeState(savedStateHandle, KEY_IS_AUTH, false)
        private set

    val stateUser = flow {
        authRepo.currentUser.collect { currentUser ->
            if (currentUser != null)
                emit(AuthState.Authenticated(currentUser))
            else
                emit(AuthState.Unauthenticated)
        }
    }.flowOn(Dispatchers.IO).catch {
        _messageAuth.trySend(showMessageForException(it, "state user pref"))
        emit(AuthState.Unauthenticated)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AuthState.Authenticating
    )

    fun signIn(
        userFieldsSignIn: SignInDTO
    ) = launchSafeIO(
        blockBefore = { isAuthenticating = true },
        blockAfter = { isAuthenticating = false },
        blockIO = { authRepo.signIn(userFieldsSignIn) },
        blockException = { _messageAuth.trySend(showMessageForException(it, "signIn")) }
    )

    fun signUp(userFieldSignUp: SignUpDTO) = launchSafeIO(
        blockBefore = { isAuthenticating = true },
        blockAfter = { isAuthenticating = false },
        blockIO = { authRepo.signUp(userFieldSignUp) },
        blockException = { _messageAuth.trySend(showMessageForException(it, "signUp")) }
    )


    fun signOut() = launchSafeIO {
        authRepo.signOut()
    }

}
