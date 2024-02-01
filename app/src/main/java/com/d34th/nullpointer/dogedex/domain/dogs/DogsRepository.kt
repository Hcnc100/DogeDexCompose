package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    val listDogs: Flow<List<DogData>>
    val isFirstRequestCameraPermission: Flow<Boolean>
    val dogsCaught: Flow<Int>
    suspend fun addDog(dogData: DogData)
    suspend fun refreshMyDogs()
    suspend fun changeIsFirstRequestCamera()
    suspend fun getRecognizeDog(dogRecognition: DogRecognition): DogData
}