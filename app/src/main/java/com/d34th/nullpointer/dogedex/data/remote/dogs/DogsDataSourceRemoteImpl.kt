package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.callApiDogsWithTimeOut
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.models.dtos.AddDogUserDTO
import com.d34th.nullpointer.dogedex.models.dtos.DogDTO

class DogsDataSourceRemoteImpl(
    private val dogsApiServices: DogsApiServices
) : DogsDataSourceRemote {

    override suspend fun getAllDogs(): List<Dog> {
        val response = callApiDogsWithTimeOut { dogsApiServices.requestAllDogs() }
        return if (response.isSuccess)
            response.data.dogs.map { it.toDog() } else throw Exception(response.message)
    }

    override suspend fun addDog(dog: Dog, token: String) {
        val addResponse = callApiDogsWithTimeOut {
            dogsApiServices.addDog(token = token, data = AddDogUserDTO(dog.index))
        }
        if (!addResponse.isSuccess) throw Exception(addResponse.message)
    }

    override suspend fun getMyDogs(
        token: String
    ): List<Dog> {
        val myDogsResponse = callApiDogsWithTimeOut { dogsApiServices.requestMyDogs(token) }
        return if (myDogsResponse.isSuccess)
            myDogsResponse.data.dogs.map { it.toDog() } else throw Exception(
            myDogsResponse.message
        )
    }

    override suspend fun getRecognizeDog(idRecognizeDog: String): Dog {
        val dogRecognize = callApiDogsWithTimeOut {
            dogsApiServices.requestRecognizeDog(idRecognizeDog)
        }
        return if (dogRecognize.isSuccess)
            dogRecognize.data.dog.toDog() else throw Exception(dogRecognize.message)
    }

    private fun DogDTO.toDog(): Dog {
        return Dog(
            index = index,
            name = name_es,
            type = dog_type,
            heightFemale = height_female.toDouble(),
            heightMale = height_male.toDouble(),
            imgUrl = image_url,
            lifeExpectancy = life_expectancy,
            temperament = temperament,
            weightFemale = weight_female,
            weightMale = weight_male
        )
    }
}


