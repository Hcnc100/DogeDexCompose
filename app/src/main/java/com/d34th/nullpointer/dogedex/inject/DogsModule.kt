package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.data.local.PrefsUser
import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSource
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSourceImpl
import com.d34th.nullpointer.dogedex.domain.DogsRepoImpl
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
object DogsModule {

    @Named("BaseUrl")
    @Provides
    fun provideBaseUrl(): String = "https://todogs.herokuapp.com/api/v1/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("BaseUrl") baseUrl: String,
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

    @Provides
    @Singleton
    fun provideDogsDataSource(
        dogsApiServices: DogsApiServices
    ): DogsDataSource = DogsDataSourceImpl(dogsApiServices)

    @Provides
    @Singleton
    fun provideDogsRepository(
        dogsDataSource: DogsDataSource,
        prefsUser: PrefsUser
    ): DogsRepoImpl = DogsRepoImpl(dogsDataSource, prefsUser)
}