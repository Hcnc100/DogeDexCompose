package com.d34th.nullpointer.dogedex.core.states

import com.d34th.nullpointer.dogedex.models.auth.data.AuthData

sealed class AuthState {
    data object Authenticating : AuthState()
    data object Unauthenticated : AuthState()
    data class Authenticated(val currentUser: AuthData) : AuthState()
}