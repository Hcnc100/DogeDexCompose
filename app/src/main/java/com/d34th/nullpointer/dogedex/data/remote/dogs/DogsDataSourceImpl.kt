package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.core.states.InternetCheck
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.models.listDogsApi.DogResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

class DogsDataSourceImpl(
    private val dogsApiServices: DogsApiServices
):DogsDataSource {

    private suspend fun <T> callApiWithTimeout(
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
                else -> " unknown error"
            }
            ApiResponse.Failure(message)
        }
    }

    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        val dogs = callApiWithTimeout {
            val dogsResponse = dogsApiServices.requestAllDogs()
            dogsResponse.data.dogs.map { it.toDog() }
        }
        return dogs
    }
}

private fun DogResponse.toDog():Dog{
    return Dog(
        id = id,
        index = index,
        name = name_es,
        type = dog_type,
        heightFemale = height_female.toDouble(),
        heightMale = height_male.toDouble(),
        imgUrl = image_url,
        lifeExpectancy = life_expectancy,
        temperament = temperament,
        weightFemale = weight_female,
        weightMale = weight_male
    )
}