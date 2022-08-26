package com.d34th.nullpointer.dogedex.domain

import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

class DogFakeRepoImpl : DogsRepository {
    override suspend fun getAllDogs(): Flow<List<Dog>> {
        TODO("Not yet implemented")
    }

    override suspend fun addDog(dog: Dog): ApiResponse<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshMyDogs(): ApiResponse<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun isNewDog(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isFirstCameraRequest(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecognizeDog(idRecognizeDog: String): ApiResponse<Dog> {
        TODO("Not yet implemented")
    }

    override suspend fun changeIsFirstRequestCamera(isFirstRequest: Boolean) {
        TODO("Not yet implemented")
    }

}