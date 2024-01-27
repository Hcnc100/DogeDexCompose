package com.d34th.nullpointer.dogedex.data.remote

import com.d34th.nullpointer.dogedex.core.states.InternetCheck
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.NO_NETWORK_MESSAGE
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.AuthResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.defaultDogs.DefaultResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.listDogs.DogsApiResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.simpleDog.DogApiResponse
import com.d34th.nullpointer.dogedex.models.dtos.AddDogUserDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.http.*

interface DogsApiServices {

    companion object {
        private const val DOG_PATH = "dogs"
        private const val SIGN_UP_PATH = "sign_up"
        private const val SIGN_IN_PATH = "sign_in"
        private const val ADD_DOG_PATH = "add_dog_to_user"
        private const val AUTH_HEADER = "AUTH-TOKEN"
        private const val DOG_USER_PATH = "get_user_dogs"
        private const val DOG_FOR_IM_PATH = "find_dog_by_ml_id"
        private const val ML_ID_PARAMETER = "ml_id"
    }

    @GET(DOG_PATH)
    suspend fun requestAllDogs(): DogsApiResponse

    @POST(SIGN_UP_PATH)
    suspend fun signUp(
        @Body data: SignUpDTO
    ): AuthResponse

    @POST(SIGN_IN_PATH)
    suspend fun signIn(
        @Body data: SignInDTO
    ): AuthResponse

    @POST(ADD_DOG_PATH)
    suspend fun addDog(
        @Header(AUTH_HEADER) token: String,
        @Body data: AddDogUserDTO
    ): DefaultResponse

    @GET(DOG_USER_PATH)
    suspend fun requestMyDogs(
        @Header(AUTH_HEADER) token: String,
    ): DogsApiResponse

    @GET(DOG_FOR_IM_PATH)
    suspend fun requestRecognizeDog(
        @Query(ML_ID_PARAMETER) idRecognizeDog: String
    ): DogApiResponse

}

suspend fun <T> callApiDogsWithTimeOut(
    timeout: Long = 3_000,
    callApi: suspend () -> T,
): T {
    if (!InternetCheck.isNetworkAvailable()) throw Exception(NO_NETWORK_MESSAGE)
    return withTimeoutOrNull(timeout) { callApi() }!!
}