package com.d34th.nullpointer.dogedex.datasource.dogs.local

import com.d34th.nullpointer.dogedex.data.dogs.local.DogDAO
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

class DogLocalDataSourceLocalImpl(
    private val dogDAO: DogDAO
) : DogLocalDataSourceLocal {
    override val listDogsSaved: Flow<List<Dog>> = dogDAO.getAllDogs()

    override suspend fun insertDog(dog: Dog) =
        dogDAO.insertDog(dog)

    override suspend fun insertAllDogs(list: List<Dog>) =
        dogDAO.insertAllDogs(list)

    override suspend fun updateAllDogs(list: List<Dog>) =
        dogDAO.insertAllDogs(list)

    override suspend fun getDogByName(nameDog: String): Dog? =
        dogDAO.getDogByName(nameDog)

    override suspend fun countHasDog(): Int =
        dogDAO.getAllHasDog().size

    override suspend fun deleteAllDogs() =
        dogDAO.deleterAllDogs()
}