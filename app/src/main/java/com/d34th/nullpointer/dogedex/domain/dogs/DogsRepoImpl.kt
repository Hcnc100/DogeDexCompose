package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.data.local.prefs.PrefsUser
import com.d34th.nullpointer.dogedex.data.local.room.DogDAO
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSource
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


class DogsRepoImpl(
    private val dogsDataSource: DogsDataSource,
    private val prefsUser: PrefsUser,
    private val dogDAO: DogDAO
) : DogsRepository {
    override suspend fun getAllDogs(): Flow<List<Dog>> {
        val isFirstLogin = prefsUser.getIsFirstLogin().first()
        if (isFirstLogin) {
            when (val dogs = dogsDataSource.getDogs()) {
                is ApiResponse.Failure -> {
                    throw Exception(dogs.message)
                }
                is ApiResponse.Success -> {
                    dogDAO.updateAllDogs(dogs.response)
                    prefsUser.changeIsFirstLogin(false)
                }
            }
        }
        return dogDAO.getAllDogs()
    }

    override suspend fun addDog(dog: Dog): ApiResponse<Unit> {
        val token = prefsUser.getUser().first().token
        return dogsDataSource.addDog(dog, token).also {
            if (it is ApiResponse.Success) {
                dogDAO.insertDog(dog.copy(hasDog = true))
            }
        }
    }

    override suspend fun refreshMyDogs(): ApiResponse<Unit> {
        val token = prefsUser.getUser().first().token
        return when (val dogsResponse = dogsDataSource.getMyDogs(token)) {
            is ApiResponse.Failure -> {
                ApiResponse.Failure(dogsResponse.message)
            }
            is ApiResponse.Success -> {
                val listMyDogs = dogsResponse.response.map { it.copy(hasDog = true) }
                dogDAO.insertDogs(listMyDogs)
                ApiResponse.Success(Unit)
            }
        }
    }

    override fun isFirstCameraRequest(): Flow<Boolean> =
        prefsUser.getIsFirstCameraRequest()

    override suspend fun changeIsFirstRequestCamera(isFirstRequest: Boolean) {
        prefsUser.changeIsFirstCameraRequest(isFirstRequest)
    }


}