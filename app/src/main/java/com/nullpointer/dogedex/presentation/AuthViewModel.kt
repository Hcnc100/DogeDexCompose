package com.nullpointer.dogedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.dogedex.core.states.AuthState
import com.nullpointer.dogedex.core.utils.ExceptionManager.showMessageForException
import com.nullpointer.dogedex.core.utils.launchSafeIO
import com.nullpointer.dogedex.domain.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
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
