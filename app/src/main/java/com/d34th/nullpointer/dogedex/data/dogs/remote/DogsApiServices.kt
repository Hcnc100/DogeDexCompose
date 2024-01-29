package com.d34th.nullpointer.dogedex.data.dogs.remote

import com.d34th.nullpointer.dogedex.BuildConfig
import com.d34th.nullpointer.dogedex.core.states.InternetCheck
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager.NO_NETWORK_MESSAGE
import com.d34th.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.d34th.nullpointer.dogedex.models.dogs.response.AddDogResponse
import com.d34th.nullpointer.dogedex.models.dogs.response.ListDogsResponse
import com.d34th.nullpointer.dogedex.models.dogs.response.ListMyDogsResponse
import com.d34th.nullpointer.dogedex.models.findDogByModel.dto.FindDogByModelDTO
import com.d34th.nullpointer.dogedex.models.findDogByModel.response.FindDogByModelResponse
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.http.*

interface DogsApiServices {

    companion object {
        private const val DOG_PATH = "dogs"
        private const val ADD_DOG_PATH = "add_dog_to_user"
        private const val DOG_USER_PATH = "get_user_dogs"
        private const val DOG_FOR_IM_PATH = "find_dog_by_ml_id"
        private const val ML_ID_PARAMETER = "ml_id"
    }

    @GET(DOG_PATH)
    suspend fun requestAllDogs(): ListDogsResponse

    @POST(ADD_DOG_PATH)
    suspend fun addDog(
        @Body addDogDTO: AddDogDTO
    ): AddDogResponse

    @GET(DOG_USER_PATH)
    suspend fun requestMyDogs(): ListMyDogsResponse

    @GET(DOG_FOR_IM_PATH)
    suspend fun requestRecognizeDog(
        @Body findDogByMLDTO: FindDogByModelDTO
    ): FindDogByModelResponse

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