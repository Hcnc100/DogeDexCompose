package com.d34th.nullpointer.dogedex.inject

import android.content.Context
import androidx.room.Room
import com.d34th.nullpointer.dogedex.data.local.dogs.room.DogDAO
import com.d34th.nullpointer.dogedex.data.local.dogs.room.DogeDexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val NAME_DB = "DOGE_DEX_DB"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DogeDexDatabase = Room.databaseBuilder(
        context,
        DogeDexDatabase::class.java,
        NAME_DB
    ).build()

    @Provides
    @Singleton
    fun provideDogsDao(
        dogeDexDatabase: DogeDexDatabase
    ): DogDAO = dogeDexDatabase.getDogDao()
}