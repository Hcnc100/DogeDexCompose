package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.data.local.dogs.DogDataSourceLocal
import com.d34th.nullpointer.dogedex.data.local.dogs.DogDataSourceLocalImpl
import com.d34th.nullpointer.dogedex.data.local.dogs.room.DogDAO
import com.d34th.nullpointer.dogedex.data.local.prefereneces.PreferencesDataSource
import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSourceRemote
import com.d34th.nullpointer.dogedex.data.remote.dogs.DogsDataSourceRemoteImpl
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepoImpl
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogsModule {

    @Provides
    @Singleton
    fun provideDogDataSourceLocal(
        dogDAO: DogDAO
    ): DogDataSourceLocal = DogDataSourceLocalImpl(dogDAO)

    @Provides
    @Singleton
    fun provideDogsDataSourceRemote(
        dogsApiServices: DogsApiServices
    ): DogsDataSourceRemote = DogsDataSourceRemoteImpl(dogsApiServices)

    @Provides
    @Singleton
    fun provideDogsRepository(
        dogDataSourceLocal: DogDataSourceLocal,
        dogsDataSourceRemote: DogsDataSourceRemote,
        preferencesDataSource: PreferencesDataSource,
    ): DogsRepository = DogsRepoImpl(
        dogDataSourceLocal,
        dogsDataSourceRemote,
        preferencesDataSource
    )
}