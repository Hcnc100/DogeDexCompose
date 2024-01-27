package com.d34th.nullpointer.dogedex.data.dogs.remote

import com.d34th.nullpointer.dogedex.BuildConfig
import com.d34th.nullpointer.dogedex.core.states.InternetCheck
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.NO_NETWORK_MESSAGE
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.defaultDogs.DefaultResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.listDogs.DogsApiResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.simpleDog.DogApiResponse
import com.d34th.nullpointer.dogedex.models.dtos.AddDogUserDTO
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.http.*

interface DogsApiServices {

    companion object {
        private const val DOG_PATH = "dogs"
        private const val ADD_DOG_PATH = "add_dog_to_user"
        private const val AUTH_HEADER = "AUTH-TOKEN"
        private const val DOG_USER_PATH = "get_user_dogs"
        private const val DOG_FOR_IM_PATH = "find_dog_by_ml_id"
        private const val ML_ID_PARAMETER = "ml_id"
    }

    @GET(DOG_PATH)
    suspend fun requestAllDogs(): DogsApiResponse

    @POST(ADD_DOG_PATH)
    suspend fun addDog(
        @Body data: AddDogUserDTO
    ): DefaultResponse

    @GET(DOG_USER_PATH)
    suspend fun requestMyDogs(
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
    return when (BuildConfig.DEBUG) {
        true -> return withTimeoutOrNull(timeout) { callApi() }!!
        else -> return callApi()
    }
}