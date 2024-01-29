package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    val listDogs: Flow<List<DogData>>
    val isFirstRequestCameraPermission: Flow<Boolean>
    suspend fun addDog(dogData: DogData)
    suspend fun refreshMyDogs()
    suspend fun firstRequestAllDogs()
    suspend fun changeIsFirstRequestCamera()
    suspend fun isNewDog(dogId: Long): Boolean
    suspend fun getRecognizeDog(dogData: DogData): Long
}