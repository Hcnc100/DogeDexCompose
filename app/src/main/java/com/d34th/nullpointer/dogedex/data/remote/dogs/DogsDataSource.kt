package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.models.signDogApi.SignApiResponse

interface DogsDataSource {
    suspend fun getDogs(): ApiResponse<List<Dog>>
    suspend fun signUp(): SignApiResponse
}