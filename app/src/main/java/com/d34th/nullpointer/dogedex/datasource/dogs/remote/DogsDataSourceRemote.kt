package com.d34th.nullpointer.dogedex.datasource.dogs.remote

import com.d34th.nullpointer.dogedex.models.Dog

interface DogsDataSourceRemote {
    suspend fun getAllDogs(): List<Dog>
    suspend fun addDog(dog: Dog)
    suspend fun getMyDogs(): List<Dog>
    suspend fun getRecognizeDog(idRecognizeDog: String): Dog
}