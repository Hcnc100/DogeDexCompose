package com.d34th.nullpointer.dogedex.datasource.dogs.local

import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogLocalDataSourceLocal {
    val listDogsSaved: Flow<List<Dog>>
    suspend fun insertDog(dog: Dog)
    suspend fun insertAllDogs(list: List<Dog>)
    suspend fun updateAllDogs(list: List<Dog>)
    suspend fun getDogByName(nameDog: String): Dog?
    suspend fun countHasDog(): Int
    suspend fun deleteAllDogs()
}