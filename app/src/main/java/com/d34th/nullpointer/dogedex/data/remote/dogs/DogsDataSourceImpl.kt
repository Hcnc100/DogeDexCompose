package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.callApiWithTimeout
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.models.authDogApi.listDogsApi.DogResponse
import com.d34th.nullpointer.dogedex.models.dtos.AddDogUserDTO

class DogsDataSourceImpl(
    private val dogsApiServices: DogsApiServices
):DogsDataSource {

    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        val dogs = callApiWithTimeout {
            val dogsResponse = dogsApiServices.requestAllDogs()
            dogsResponse.data.dogs.map { it.toDog() }
        }
        return dogs
    }

    override suspend fun addDog(dog: Dog, token: String): ApiResponse<Unit> {
        val addResponse = callApiWithTimeout {
            val addResponse = dogsApiServices.addDog(token = token, data = AddDogUserDTO(dog.id))
            if (!addResponse.is_success) throw Exception(addResponse.message)
        }
        return addResponse
    }

    override suspend fun getMyDogs(
        token: String
    ): ApiResponse<List<Dog>> {
        val dogs = callApiWithTimeout {
            val dogsResponse = dogsApiServices.requestMyDogs(token)
            dogsResponse.data.dogs.map { it.toDog() }
        }
        return dogs
    }

    private fun DogResponse.toDog(): Dog {
        return Dog(
            id = id,
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


