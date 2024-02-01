package com.nullpointer.dogedex.data.dogs.remote

import com.nullpointer.dogedex.BuildConfig
import com.nullpointer.dogedex.core.states.InternetCheck
import com.nullpointer.dogedex.core.utils.ExceptionManager.NO_NETWORK_MESSAGE
import com.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.nullpointer.dogedex.models.dogs.response.AddDogResponse
import com.nullpointer.dogedex.models.dogs.response.ListDogsResponse
import com.nullpointer.dogedex.models.dogs.response.ListMyDogsResponse
import com.nullpointer.dogedex.models.findDogByModel.response.FindDogByModelResponse
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
        @Query(ML_ID_PARAMETER) modelId: String
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