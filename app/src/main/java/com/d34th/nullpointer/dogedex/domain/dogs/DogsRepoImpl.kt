package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.d34th.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.d34th.nullpointer.dogedex.datasource.dogs.remote.DogsDataSourceRemote
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


class DogsRepoImpl(
    private val dogLocalDataSourceLocal: DogLocalDataSourceLocal,
    private val dogsDataSourceRemote: DogsDataSourceRemote,
    private val settingsLocalDataSource: SettingsLocalDataSource
) : DogsRepository {

    override val listDogs: Flow<List<Dog>> = dogLocalDataSourceLocal.listDogsSaved

    override suspend fun firstRequestAllDogs() {
        val isFirstLogin = settingsLocalDataSource.settingsData.first()?.isFirstLogin ?: true
        if (isFirstLogin) {
            val listDogs = dogsDataSourceRemote.getAllDogs()
            dogLocalDataSourceLocal.updateAllDogs(listDogs)
            // * change isFirstLogin
            settingsLocalDataSource.changeIsFirstLoginUser()
        }
    }

    override val isFirstRequestCameraPermission: Flow<Boolean> =
        settingsLocalDataSource.isFirstRequestCameraPermission

    override suspend fun addDog(dog: Dog) {
        dogsDataSourceRemote.addDog(dog)
        dogLocalDataSourceLocal.insertDog(dog.copy(hasDog = true))
    }

    override suspend fun refreshMyDogs() {
        val isFirstLogin = settingsLocalDataSource.isFirstLoadingUser.first()
        if (!isFirstLogin) {
            val listMyDogsServer = dogsDataSourceRemote.getMyDogs()
            if (listMyDogsServer.size != dogLocalDataSourceLocal.countHasDog()) {
                val newLisHasDog = listMyDogsServer.map { it.copy(hasDog = true) }
                dogLocalDataSourceLocal.insertAllDogs(newLisHasDog)
            }
        }
    }

    override suspend fun isNewDog(name: String): Boolean {
        val dog = dogLocalDataSourceLocal.getDogByName(name)
        return dog != null && !dog.hasDog
    }

    override suspend fun changeIsFirstRequestCamera() =
        settingsLocalDataSource.changeIsFirstRequestCamera()

    override suspend fun getRecognizeDog(idRecognizeDog: String): Dog =
        dogsDataSourceRemote.getRecognizeDog(idRecognizeDog)

}