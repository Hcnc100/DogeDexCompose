package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.CredentialUser
import com.d34th.nullpointer.dogedex.models.listDogsApi.DogsApiResponse
import com.d34th.nullpointer.dogedex.models.signDogApi.SignApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DogsApiServices {

    @GET("dogs")
    suspend fun requestAllDogs(): DogsApiResponse

    @POST("sign_up")
    suspend fun signUp(
        @Body data: CredentialUser
    ): SignApiResponse
}