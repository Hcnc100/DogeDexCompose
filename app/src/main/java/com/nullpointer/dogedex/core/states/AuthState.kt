package com.nullpointer.dogedex.core.states

import com.nullpointer.dogedex.models.auth.data.AuthData

sealed class AuthState {
    data object Authenticating : AuthState()
    data object Unauthenticated : AuthState()
    data class Authenticated(val currentUser: AuthData) : AuthState()
}