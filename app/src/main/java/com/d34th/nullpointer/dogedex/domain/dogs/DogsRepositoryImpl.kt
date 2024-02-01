package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.d34th.nullpointer.dogedex.datasource.dogs.remote.DogsRemoteDataSourceRemote
import com.d34th.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.d34th.nullpointer.dogedex.models.findDogByModel.dto.FindDogByModelDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow


class DogsRepositoryImpl(
    private val dogLocalDataSourceLocal: DogLocalDataSourceLocal,
    private val settingsLocalDataSource: SettingsLocalDataSource,
    private val dogsRemoteDataSourceRemote: DogsRemoteDataSourceRemote,
) : DogsRepository {

    override val dogsCaught: Flow<Int> = dogLocalDataSourceLocal.countHasDog()

    override val listDogs: Flow<List<DogData>> = dogLocalDataSourceLocal.listDogsSaved

    override val isFirstRequestCameraPermission: Flow<Boolean> =
        settingsLocalDataSource.isFirstRequestCameraPermission

    override suspend fun addDog(dogData: DogData) {
        val addDogDTO = AddDogDTO.fromDogData(dogData)
        dogsRemoteDataSourceRemote.addDog(addDogDTO)
        dogLocalDataSourceLocal.insertDog(dogData.copy(hasDog = true))
    }

    override suspend fun refreshMyDogs() = coroutineScope {

        // * get all dogs from server if not exist in local storage
        val taskGetAllDogs = async {
            val hasAllDogs = dogLocalDataSourceLocal.countAllDogs() != 0
            if (hasAllDogs) {
                emptyList()
            } else {
                dogsRemoteDataSourceRemote.getAllDogs()
            }
        }
        // * get my dogs from server
        val taskMyDogs = async {
            dogsRemoteDataSourceRemote.getMyDogs()
        }

        // * wait for both tasks to finish
        val listDogsServer = taskGetAllDogs.await()
        val listMyDogsServer = taskMyDogs.await()

        // * update local storage
        if (listDogsServer.isNotEmpty()) {
            dogLocalDataSourceLocal.updateAllDogs(listDogsServer)
        }
        // * insert my dogs
        if (listMyDogsServer.isNotEmpty()) {
            dogLocalDataSourceLocal.insertAllDogs(listMyDogsServer)
        }
    }

    override suspend fun getRecognizeDog(dogRecognition: DogRecognition): DogData {
        val findDogByModelDTO = FindDogByModelDTO.fromDogRecognition(dogRecognition)
        val dogId = dogsRemoteDataSourceRemote.getRecognizeDog(findDogByModelDTO)
        return dogLocalDataSourceLocal.getDogById(dogId)!!
    }

    override suspend fun changeIsFirstRequestCamera() =
        settingsLocalDataSource.changeIsFirstRequestCamera()

}