package com.nullpointer.dogedex.inject

import com.nullpointer.dogedex.data.dogs.local.DogDAO
import com.nullpointer.dogedex.data.dogs.remote.DogsApiServices
import com.nullpointer.dogedex.database.DogeDexDatabase
import com.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocalImpl
import com.nullpointer.dogedex.datasource.dogs.remote.DogsRemoteDataSourceRemote
import com.nullpointer.dogedex.datasource.dogs.remote.DogsRemoteDataSourceRemoteImpl
import com.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.nullpointer.dogedex.domain.dogs.DogsRepository
import com.nullpointer.dogedex.domain.dogs.DogsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogsModule {

    @Provides
    @Singleton
    fun provideDogsDao(
        dogeDexDatabase: DogeDexDatabase
    ): DogDAO = dogeDexDatabase.getDogDao()

    @Singleton
    @Provides
    fun provideDogsApiServices(
        retrofit: Retrofit
    ): DogsApiServices =
        retrofit.create(DogsApiServices::class.java)

    @Provides
    @Singleton
    fun provideDogDataSourceLocal(
        dogDAO: DogDAO
    ): DogLocalDataSourceLocal = DogLocalDataSourceLocalImpl(dogDAO)

    @Provides
    @Singleton
    fun provideDogsDataSourceRemote(
        dogsApiServices: DogsApiServices
    ): DogsRemoteDataSourceRemote = DogsRemoteDataSourceRemoteImpl(dogsApiServices)

    @Provides
    @Singleton
    fun provideDogsRepository(
        dogLocalDataSourceLocal: DogLocalDataSourceLocal,
        dogsRemoteDataSourceRemote: DogsRemoteDataSourceRemote,
        settingsLocalDataSource: SettingsLocalDataSource,
    ): DogsRepository = DogsRepositoryImpl(
        dogLocalDataSourceLocal = dogLocalDataSourceLocal,
        settingsLocalDataSource = settingsLocalDataSource,
        dogsRemoteDataSourceRemote = dogsRemoteDataSourceRemote
    )
}