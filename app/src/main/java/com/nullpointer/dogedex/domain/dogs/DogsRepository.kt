package com.nullpointer.dogedex.domain.dogs

import com.nullpointer.dogedex.ia.DogRecognition
import com.nullpointer.dogedex.models.dogs.data.DogData
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