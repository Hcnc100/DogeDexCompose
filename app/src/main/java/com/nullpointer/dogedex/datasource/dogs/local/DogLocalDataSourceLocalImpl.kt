package com.nullpointer.dogedex.datasource.dogs.local

import com.nullpointer.dogedex.data.dogs.local.DogDAO
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.models.dogs.entity.DogEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DogLocalDataSourceLocalImpl(
    private val dogDAO: DogDAO
) : DogLocalDataSourceLocal {
    override val listDogsSaved: Flow<List<DogData>> = dogDAO.getAllDogs().map { list ->
        list.map { DogData.fromDogEntity(it) }
    }

    override suspend fun insertDog(dogData: DogData) =
        dogDAO.insertDog(DogEntity.fromDogData(dogData))

    override suspend fun insertAllDogs(list: List<DogData>) {
        val listEntities = list.map { DogEntity.fromDogData(it) }
        dogDAO.insertAllDogs(listEntities)
    }

    override suspend fun updateAllDogs(list: List<DogData>) {
        val listEntities = list.map { DogEntity.fromDogData(it) }
        dogDAO.updateAllDogs(listEntities)
    }

    override suspend fun getDogById(dogId: Long): DogData? {
        val response = dogDAO.getDogById(dogId)
        return response?.let { DogData.fromDogEntity(it) }
    }

    override fun countHasDog(): Flow<Int> =
        dogDAO.getCountHasDog()

    override suspend fun countAllDogs(): Int =
        dogDAO.getCountAllDogs()

    override suspend fun deleteAllDogs() =
        dogDAO.deleterAllDogs()
}