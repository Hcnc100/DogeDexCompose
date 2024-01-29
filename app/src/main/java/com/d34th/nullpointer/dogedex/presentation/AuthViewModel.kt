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



    private val _messageAuth = Channel<Int>()
    val messageAuth = _messageAuth.receiveAsFlow()


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



    fun signOut() = launchSafeIO {
        authRepo.signOut()
    }

}
