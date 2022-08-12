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
):DogsRepository {
    override suspend fun getDogs(): Flow<List<Dog>> {
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

}