package com.d34th.nullpointer.dogedex.models

sealed class ApiResponse<T> {
    data class Success<T>(val response: T) : ApiResponse<T>()
    data class Failure<T>(val message: String) : ApiResponse<T>()
}