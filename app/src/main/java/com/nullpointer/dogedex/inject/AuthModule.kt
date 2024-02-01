package com.nullpointer.dogedex.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nullpointer.dogedex.data.auth.local.AuthDataStore
import com.nullpointer.dogedex.data.auth.remote.AuthApiServices
import com.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSource
import com.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSourceImpl
import com.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSource
import com.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSourceImpl
import com.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.nullpointer.dogedex.domain.auth.AuthRepoImpl
import com.nullpointer.dogedex.domain.auth.AuthRepository
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