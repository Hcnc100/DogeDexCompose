package com.d34th.nullpointer.dogedex.data.remote

import com.d34th.nullpointer.dogedex.core.states.InternetCheck
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.authDogApi.AuthResponse
import com.d34th.nullpointer.dogedex.models.authDogApi.DefaultResponse
import com.d34th.nullpointer.dogedex.models.authDogApi.listDogsApi.DogsApiResponse
import com.d34th.nullpointer.dogedex.models.dtos.AddDogUserDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import timber.log.Timber

interface DogsApiServices {

    @GET("dogs")
    suspend fun requestAllDogs(): DogsApiResponse

    @POST("sign_up")
    suspend fun signUp(
        @Body data: SignUpDTO
    ): AuthResponse

    @POST("sign_in")
    suspend fun signIn(
        @Body data: SignInDTO
    ): AuthResponse

    @POST("add_dog_to_user")
    suspend fun addDog(
        @Header("AUTH-TOKEN") token: String,
        @Body data: AddDogUserDTO
    ): DefaultResponse

    @GET("get_user_dogs")
    suspend fun requestMyDogs(
        @Header("AUTH-TOKEN") token: String,
    ): DogsApiResponse

}

suspend fun <T> callApiWithTimeout(
    timeout: Long = 3_000,
    callApi: suspend () -> T,
): ApiResponse<T> {
    return try {
        if (!InternetCheck.isNetworkAvailable()) return ApiResponse.Failure("Network is not available")
        withTimeout(timeout) {
            ApiResponse.Success(callApi())
        }
    } catch (e: Exception) {
        val message = when (e) {
            is TimeoutCancellationException -> "El servidor no responde"
            is CancellationException -> throw e
            else -> when (e.message) {
                "sign_up_error" -> "Error al crear cuenta"
                "sign_in_error" -> "Error al autenticar"
                "user_not_found" -> "El usuario no existe"
                "user_already_exists" -> "El usuario ya existe"
                else -> {
                    Timber.d("Error api $e")
                    "unknown error"
                }
            }
        }
        ApiResponse.Failure(message)
    }
}