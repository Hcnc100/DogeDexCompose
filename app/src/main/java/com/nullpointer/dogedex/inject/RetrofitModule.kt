package com.nullpointer.dogedex.inject

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nullpointer.dogedex.data.auth.remote.TokenInterceptor
import com.nullpointer.dogedex.data.auth.remote.TokenRefreshAuthenticator
import com.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSource
import com.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val NAME_BASE_URL = "BaseUrl"
    private const val BASE_URL_API = "https://todogs.herokuapp.com/api/v1/"

    @Named(NAME_BASE_URL)
    @Provides
    fun provideBaseUrl(): String = BASE_URL_API

    @Singleton
    @Provides
    fun provideSerialization(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideAuthenticator(
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSourceProvider: Provider<AuthRemoteDataSource>
    ): TokenRefreshAuthenticator = TokenRefreshAuthenticator(
        authLocalDataSource = authLocalDataSource,
        authRemoteDataSourceProvider = authRemoteDataSourceProvider
    )

    @Singleton
    @Provides
    fun provideInterceptor(
        authLocalDataSource: AuthLocalDataSource
    ): TokenInterceptor = TokenInterceptor(authLocalDataSource)

    @Singleton
    @Provides
    fun provideLoggerClient(
        tokenInterceptor: TokenInterceptor,
        tokenRefreshAuthenticator: TokenRefreshAuthenticator,
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
        .addInterceptor(tokenInterceptor)
        .authenticator(tokenRefreshAuthenticator)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        client: OkHttpClient,
        @Named(NAME_BASE_URL) baseUrl: String,
        convertFactory: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder().addConverterFactory(convertFactory).client(client).baseUrl(baseUrl)
            .build()


}