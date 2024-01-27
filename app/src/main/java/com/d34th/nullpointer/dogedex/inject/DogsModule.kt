package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.d34th.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocalImpl
import com.d34th.nullpointer.dogedex.data.dogs.local.DogDAO
import com.d34th.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.d34th.nullpointer.dogedex.data.dogs.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.datasource.dogs.remote.DogsDataSourceRemote
import com.d34th.nullpointer.dogedex.datasource.dogs.remote.DogsDataSourceRemoteImpl
import com.d34th.nullpointer.dogedex.database.DogeDexDatabase
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepoImpl
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
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
    ): DogsDataSourceRemote = DogsDataSourceRemoteImpl(dogsApiServices)

    @Provides
    @Singleton
    fun provideDogsRepository(
        dogLocalDataSourceLocal: DogLocalDataSourceLocal,
        dogsDataSourceRemote: DogsDataSourceRemote,
        settingsLocalDataSource: SettingsLocalDataSource,
    ): DogsRepository = DogsRepoImpl(
        dogLocalDataSourceLocal,
        dogsDataSourceRemote,
        settingsLocalDataSource
    )
}