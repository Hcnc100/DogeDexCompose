package com.d34th.nullpointer.dogedex.domain

import com.d34th.nullpointer.dogedex.data.local.PrefsUser
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSource
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.first

class DogsRepoImpl(
    private val dogsDataSource: DogsDataSource,
    private val prefsUser: PrefsUser
):DogsRepository {
    override suspend fun getDogs(): ApiResponse<List<Dog>> =
        dogsDataSource.getDogs()

    override suspend fun addDog(dog: Dog): ApiResponse<Unit> {
        val token = prefsUser.getUser().first().token
        return dogsDataSource.addDog(dog, token)
    }

}