package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices.Companion.BASE_URL_API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogApiServiceModule {
    private const val NAME_BASE_URL = "BaseUrl"

    @Named(NAME_BASE_URL)
    @Provides
    fun provideBaseUrl(): String = BASE_URL_API

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named(NAME_BASE_URL) baseUrl: String,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideDogsApiServices(
        retrofit: Retrofit,
    ): DogsApiServices = retrofit.create(DogsApiServices::class.java)
}