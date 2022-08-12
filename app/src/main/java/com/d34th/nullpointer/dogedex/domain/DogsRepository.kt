package com.d34th.nullpointer.dogedex.domain

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog

interface DogsRepository {
    suspend fun getDogs(): ApiResponse<List<Dog>>
    suspend fun addDog(dog: Dog): ApiResponse<Unit>
}
