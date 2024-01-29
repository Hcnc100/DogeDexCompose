package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.d34th.nullpointer.dogedex.datasource.dogs.remote.DogsRemoteDataSourceRemote
import com.d34th.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.models.dogs.dto.AddDogDTO
import com.d34th.nullpointer.dogedex.models.findDogByModel.dto.FindDogByModelDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


class DogsRepositoryImpl(
    private val dogLocalDataSourceLocal: DogLocalDataSourceLocal,
    private val dogsRemoteDataSourceRemote: DogsRemoteDataSourceRemote,
    private val settingsLocalDataSource: SettingsLocalDataSource
) : DogsRepository {

    override val listDogs: Flow<List<DogData>> = dogLocalDataSourceLocal.listDogsSaved

    override suspend fun firstRequestAllDogs() {
        val isFirstLogin = settingsLocalDataSource.settingsData.first()?.isFirstLogin ?: true
        if (isFirstLogin) {
            val listDogs = dogsRemoteDataSourceRemote.getAllDogs()
            dogLocalDataSourceLocal.updateAllDogs(listDogs)
            // * change isFirstLogin
            settingsLocalDataSource.changeIsFirstLoginUser()
        }
    }

    override val isFirstRequestCameraPermission: Flow<Boolean> =
        settingsLocalDataSource.isFirstRequestCameraPermission

    override suspend fun addDog(dogData: DogData) {
        val addDogDTO = AddDogDTO.fromDogData(dogData)
        dogsRemoteDataSourceRemote.addDog(addDogDTO)
        dogLocalDataSourceLocal.insertDog(dogData.copy(hasDog = true))
    }

    override suspend fun refreshMyDogs() {
        val isFirstLogin = settingsLocalDataSource.isFirstLoadingUser.first()
        if (!isFirstLogin) {
            val listMyDogsServer = dogsRemoteDataSourceRemote.getMyDogs()
            if (listMyDogsServer.size != dogLocalDataSourceLocal.countHasDog()) {
                val newLisHasDog = listMyDogsServer.map { it.copy(hasDog = true) }
                dogLocalDataSourceLocal.insertAllDogs(newLisHasDog)
            }
        }
    }

    override suspend fun isNewDog(dogId: Long): Boolean {
        val dog = dogLocalDataSourceLocal.getDogById(dogId)
        return dog?.hasDog != true
    }

    override suspend fun getRecognizeDog(dogData: DogData): Long {
        val findDogByModelDTO = FindDogByModelDTO.fromDogData(dogData)
        return dogsRemoteDataSourceRemote.getRecognizeDog(findDogByModelDTO)
    }

    override suspend fun changeIsFirstRequestCamera() =
        settingsLocalDataSource.changeIsFirstRequestCamera()


}