package com.nullpointer.dogedex.core.states

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data object Failure : Resource<Nothing>()
}