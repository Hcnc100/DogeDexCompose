package com.nullpointer.dogedex.datasource.dogs.local

import com.nullpointer.dogedex.models.dogs.data.DogData
import kotlinx.coroutines.flow.Flow

interface DogLocalDataSourceLocal {
    val listDogsSaved: Flow<List<DogData>>
    suspend fun insertDog(dogData: DogData)
    suspend fun insertAllDogs(list: List<DogData>)
    suspend fun updateAllDogs(list: List<DogData>)
    suspend fun getDogById(dogId: Long): DogData?
    fun countHasDog(): Flow<Int>

    suspend fun countAllDogs(): Int
    suspend fun deleteAllDogs()
}