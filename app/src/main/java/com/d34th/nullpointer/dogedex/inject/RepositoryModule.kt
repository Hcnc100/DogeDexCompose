package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.domain.DogsRepoImpl
import com.d34th.nullpointer.dogedex.domain.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideDogsRepository(
        dogsRepoImpl: DogsRepoImpl
    ):DogsRepository
}