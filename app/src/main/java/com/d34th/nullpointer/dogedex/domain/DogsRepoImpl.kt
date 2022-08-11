package com.d34th.nullpointer.dogedex.domain

import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSource
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog

class DogsRepoImpl(
    private val dogsDataSource: DogsDataSource
):DogsRepository {
    override suspend fun getDogs(): ApiResponse<List<Dog>> =
        dogsDataSource.getDogs()

}