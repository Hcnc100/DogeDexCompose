package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.data.local.PrefsUser
import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.auth.AuthDataSource
import com.d34th.nullpointer.dogedex.data.remote.auth.AuthDataSourceImpl
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(
        dogsApiServices: DogsApiServices
    ): AuthDataSource = AuthDataSourceImpl(dogsApiServices)

    @Provides
    @Singleton
    fun provideAuthRepository(
        prefsUser: PrefsUser,
        authDataSource: AuthDataSource
    ): AuthRepoImpl = AuthRepoImpl(prefsUser, authDataSource)
}