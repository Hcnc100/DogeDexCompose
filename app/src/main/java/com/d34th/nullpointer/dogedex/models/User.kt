package com.d34th.nullpointer.dogedex.models

data class User(
    val id: Long = -1,
    val email: String = "",
    val token: String = ""
) {
    val isAuth: Boolean get() = id != -1L

    companion object {

    }
}