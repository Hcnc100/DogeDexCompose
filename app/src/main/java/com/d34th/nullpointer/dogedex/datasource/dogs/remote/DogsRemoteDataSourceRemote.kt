package com.d34th.nullpointer.dogedex.datasource.dogs.remote

import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.d34th.nullpointer.dogedex.models.findDogByModel.dto.FindDogByModelDTO

interface DogsRemoteDataSourceRemote {
    suspend fun getAllDogs(): List<DogData>
    suspend fun addDog(addDogDTO: AddDogDTO)
    suspend fun getMyDogs(): List<DogData>
    suspend fun getRecognizeDog(findDogByModelDTO: FindDogByModelDTO): Long
}