package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog

interface DogsDataSource {
    suspend fun getDogs(): ApiResponse<List<Dog>>
    suspend fun addDog(dog: Dog, token: String): ApiResponse<Unit>
    suspend fun getMyDogs(token: String): ApiResponse<List<Dog>>
}