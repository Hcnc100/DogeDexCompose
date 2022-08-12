package com.d34th.nullpointer.dogedex.core.states

import com.d34th.nullpointer.dogedex.models.User

sealed class AuthState {
    object Authenticating : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val currentUser: User) : AuthState()
}