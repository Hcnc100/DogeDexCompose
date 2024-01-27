package com.d34th.nullpointer.dogedex.core.states

import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.auth.data.AuthData

sealed class AuthState {
    object Authenticating : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val currentUser: AuthData) : AuthState()
}