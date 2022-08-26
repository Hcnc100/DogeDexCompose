package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    val listDogs: Flow<List<Dog>>
    val isFirstRequestCameraPermission: Flow<Boolean>
    suspend fun addDog(dog: Dog)
    suspend fun refreshMyDogs()
    suspend fun changeIsFirstRequestCamera()
    suspend fun isNewDog(name: String): Boolean
    suspend fun getRecognizeDog(idRecognizeDog: String): Dog
}