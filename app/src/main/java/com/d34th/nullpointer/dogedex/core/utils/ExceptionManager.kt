package com.d34th.nullpointer.dogedex.core.utils

import com.d34th.nullpointer.dogedex.R
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException

object ExceptionManager {
    const val NO_NETWORK_MESSAGE = "NO_NETWORK_MESSAGE"
    private const val USER_NOT_FOUND = "user_not_found"
    private const val INVALID_CREDENTIAL = "unauthorized"
    private const val ERROR_SING_UP = "sign_up_error"
    private const val ERROR_SIGN_IN = "sign_in_error"
    private const val ERROR_USER_ALREADY_REGISTERED = "user_already_exists"
    private const val ERROR_SERVER = "Internal Server Error"

    fun showMessageForException(
        throwable: Throwable,
        message: String = ""
    ) = showMessageForException(Exception(throwable), message)

    fun showMessageForException(
        exception: Exception,
        message: String = ""
    ): Int {
        return when (exception) {
            is HttpException -> R.string.error_credentials_sign_up
            is ConnectException, is NullPointerException -> R.string.error_server_connect
            else -> {
                when (exception.message) {
                    INVALID_CREDENTIAL -> R.string.error_credentials_sign_up
                    NO_NETWORK_MESSAGE -> R.string.error_no_network_avariable
                    USER_NOT_FOUND -> R.string.error_user_no_register
                    ERROR_SING_UP -> R.string.error_sign_up
                    ERROR_SIGN_IN -> R.string.error_sign_in
                    ERROR_USER_ALREADY_REGISTERED -> R.string.error_user_already_registered
                    ERROR_SERVER -> R.string.internal_server_error
                    else -> {
                        Timber.e("Unknown exception ${"$message "}-> $exception")
                        R.string.error_unknow
                    }
                }
            }
        }
    }
}