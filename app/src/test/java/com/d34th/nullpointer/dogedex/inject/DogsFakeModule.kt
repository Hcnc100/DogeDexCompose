package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.domain.DogFakeRepoImpl
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DogsModule::class]
)
abstract class DogsFakeModule {

    @Singleton
    @Binds
    abstract fun bindAnalyticsService(
        dogFakeRepoImpl: DogFakeRepoImpl
    ): DogsRepository
}