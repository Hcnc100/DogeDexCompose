package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.Dog

interface DogsDataSourceRemote {
    suspend fun getAllDogs(): List<Dog>
    suspend fun addDog(dog: Dog, token: String)
    suspend fun getMyDogs(token: String): List<Dog>
    suspend fun getRecognizeDog(idRecognizeDog: String): Dog
}