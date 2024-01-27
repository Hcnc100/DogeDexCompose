package com.d34th.nullpointer.dogedex.inject

import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogApiServiceModule {
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
    fun provideLoggerClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named(NAME_BASE_URL) baseUrl: String,
        convertFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(convertFactory)
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideDogsApiServices(
        retrofit: Retrofit,
    ): DogsApiServices = retrofit.create(DogsApiServices::class.java)
}