package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getAllDogs(): Flow<List<Dog>>
    suspend fun addDog(dog: Dog): ApiResponse<Unit>
    suspend fun refreshMyDogs(): ApiResponse<Unit>
    fun isFirstCameraRequest(): Flow<Boolean>
    suspend fun changeIsFirstRequestCamera(isFirstRequest: Boolean)
}