package com.d34th.nullpointer.dogedex.data

import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSource
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog

class DogFakeDataSource : DogsDataSource {
    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        TODO("Not yet implemented")
    }

    override suspend fun addDog(dog: Dog, token: String): ApiResponse<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyDogs(token: String): ApiResponse<List<Dog>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecognizeDog(idRecognizeDog: String): ApiResponse<Dog> {
        TODO("Not yet implemented")
    }
}