package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.data.local.dogs.DogDataSourceLocal
import com.d34th.nullpointer.dogedex.data.local.prefereneces.PreferencesDataSource
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSourceRemote
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


class DogsRepoImpl(
    private val dogDataSourceLocal: DogDataSourceLocal,
    private val dogsDataSourceRemote: DogsDataSourceRemote,
    private val preferencesDataSource: PreferencesDataSource
) : DogsRepository {

    override val listDogs: Flow<List<Dog>> = flow {
        val isFirstLogin = preferencesDataSource.isFirstLoadingUser.first()
        if (isFirstLogin) {
            val listDogs = dogsDataSourceRemote.getAllDogs()
            dogDataSourceLocal.updateAllDogs(listDogs)
            // * change isFirstLogin
            preferencesDataSource.changeIsFirstLoginUser()
            refreshMyDogs()
        }

        dogDataSourceLocal.listDogsSaved.collect(::emit)
    }

    override val isFirstRequestCameraPermission: Flow<Boolean> =
        preferencesDataSource.isFirstRequestCameraPermission

    override suspend fun addDog(dog: Dog) {
        val userToken = preferencesDataSource.currentUser.first().token
        dogsDataSourceRemote.addDog(dog, userToken)
        dogDataSourceLocal.insertDog(dog.copy(hasDog = true))
    }

    override suspend fun refreshMyDogs() {
        val isFirstLogin = preferencesDataSource.isFirstLoadingUser.first()
        if (!isFirstLogin) {
            val userToken = preferencesDataSource.currentUser.first().token
            val listMyDogsServer = dogsDataSourceRemote.getMyDogs(userToken)
            if (listMyDogsServer.size != dogDataSourceLocal.countHasDog()) {
                val newLisHasDog = listMyDogsServer.map { it.copy(hasDog = true) }
                dogDataSourceLocal.insertAllDogs(newLisHasDog)
            }
        }
    }

    override suspend fun isNewDog(name: String): Boolean {
        val dog = dogDataSourceLocal.getDogByName(name)
        return dog != null && !dog.hasDog
    }

    override suspend fun changeIsFirstRequestCamera() =
        preferencesDataSource.changeIsFirstRequestCamera()

    override suspend fun getRecognizeDog(idRecognizeDog: String): Dog =
        getRecognizeDog(idRecognizeDog)

}