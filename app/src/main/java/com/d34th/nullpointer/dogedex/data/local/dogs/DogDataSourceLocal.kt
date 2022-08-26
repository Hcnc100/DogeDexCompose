package com.d34th.nullpointer.dogedex.data.local.dogs

import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogDataSourceLocal {
    val listDogsSaved: Flow<List<Dog>>
    suspend fun insertDog(dog: Dog)
    suspend fun insertAllDogs(list: List<Dog>)
    suspend fun updateAllDogs(list: List<Dog>)
    suspend fun getDogByName(nameDog: String): Dog?
    suspend fun countHasDog(): Int
}