package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.domain.auth.AuthRepoImpl
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepoImpl
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
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
    ): DogsRepository

    @Binds
    @Singleton
    abstract fun provideAuthRepository(
        authRepoImpl: AuthRepoImpl
    ): AuthRepository
}