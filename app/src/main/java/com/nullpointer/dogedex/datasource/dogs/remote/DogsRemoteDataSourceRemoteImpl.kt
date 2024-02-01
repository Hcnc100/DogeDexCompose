package com.nullpointer.dogedex.datasource.dogs.remote

import com.nullpointer.dogedex.data.dogs.remote.DogsApiServices
import com.nullpointer.dogedex.data.dogs.remote.callApiDogsWithTimeOut
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.nullpointer.dogedex.models.findDogByModel.dto.FindDogByModelDTO

class DogsRemoteDataSourceRemoteImpl(
    private val dogsApiServices: DogsApiServices
) : DogsRemoteDataSourceRemote {

    override suspend fun getAllDogs(): List<DogData> {
        val response = callApiDogsWithTimeOut { dogsApiServices.requestAllDogs() }
        return when (response.isSuccess) {
            true -> response.data!!.dogs!!.map(DogData::fromDogResponse)
            else -> throw Exception(response.message)
        }
    }

    override suspend fun addDog(addDogDTO: AddDogDTO) {
        val addResponse = callApiDogsWithTimeOut {
            dogsApiServices.addDog(addDogDTO)
        }
        return when (addResponse.isSuccess) {
            true -> Unit
            else -> throw Exception(addResponse.message)
        }
    }

    override suspend fun getMyDogs(): List<DogData> {
        val myDogsResponse = callApiDogsWithTimeOut { dogsApiServices.requestMyDogs() }
        return when (myDogsResponse.isSuccess) {
            true -> myDogsResponse.data!!.dogs!!.map(DogData::fromMyDogResponse)
            else -> throw Exception(myDogsResponse.message)
        }
    }

    override suspend fun getRecognizeDog(
        findDogByModelDTO: FindDogByModelDTO
    ): Long {
        val dogRecognize = callApiDogsWithTimeOut {
            dogsApiServices.requestRecognizeDog(findDogByModelDTO.modelId)
        }

        return when (dogRecognize.isSuccess) {
            true -> dogRecognize.data!!.dog!!.id!!
            else -> throw Exception(dogRecognize.message)
        }
    }

}


