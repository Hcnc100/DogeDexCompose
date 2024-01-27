package com.d34th.nullpointer.dogedex.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.d34th.nullpointer.dogedex.data.auth.local.AuthDataStore
import com.d34th.nullpointer.dogedex.data.auth.remote.AuthApiServices
import com.d34th.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.d34th.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.d34th.nullpointer.dogedex.data.dogs.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSource
import com.d34th.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSourceImpl
import com.d34th.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSource
import com.d34th.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSourceImpl
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepoImpl
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiServices(
        retrofit: Retrofit
    ): AuthApiServices = retrofit.create(AuthApiServices::class.java)

    @Provides
    @Singleton
    fun provideAuthDataStore(
        dataStore: DataStore<Preferences>
    ):AuthDataStore = AuthDataStore(dataStore)

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        authDataStore: AuthDataStore
    ): AuthLocalDataSource = AuthLocalDataSourceImpl(authDataStore)


    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authApiServices: AuthApiServices
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(authApiServices)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource,
        settingsLocalDataSource: SettingsLocalDataSource,
        dogsLocalDataSourceLocal: DogLocalDataSourceLocal,
    ): AuthRepository = AuthRepoImpl(
        authLocalDataSource = authLocalDataSource,
        authRemoteDataSource = authRemoteDataSource,
        settingsLocalDataSource = settingsLocalDataSource,
        dogLocalDataSourceLocal = dogsLocalDataSourceLocal
    )
}