package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.dogsApi.DogsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsApiServices {

    @GET("dogs")
    suspend fun requestAllDogs(): DogsApiResponse
}