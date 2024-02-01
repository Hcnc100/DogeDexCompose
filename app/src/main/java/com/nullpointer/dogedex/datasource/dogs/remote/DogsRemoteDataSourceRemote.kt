package com.nullpointer.dogedex.datasource.dogs.remote

import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.nullpointer.dogedex.models.findDogByModel.dto.FindDogByModelDTO

interface DogsRemoteDataSourceRemote {
    suspend fun getAllDogs(): List<DogData>
    suspend fun addDog(addDogDTO: AddDogDTO)
    suspend fun getMyDogs(): List<DogData>
    suspend fun getRecognizeDog(findDogByModelDTO: FindDogByModelDTO): Long
}