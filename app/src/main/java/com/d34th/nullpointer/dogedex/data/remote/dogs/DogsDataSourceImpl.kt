package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.Dog

class DogsDataSourceImpl(
    private val dogsApiServices: DogsApiServices
):DogsDataSource {
    override suspend fun getDogs(): List<Dog>{
        val dogsResponse=dogsApiServices.requestAllDogs()
        return emptyList()
    }
}