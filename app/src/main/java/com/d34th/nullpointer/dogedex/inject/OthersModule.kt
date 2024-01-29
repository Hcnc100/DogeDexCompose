package com.d34th.nullpointer.dogedex.inject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.d34th.nullpointer.dogedex.database.DogeDexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OthersModule {

    private const val NAME_DB = "DOGE_DEX_DB"
    private const val NAME_SETTINGS = "DOGS_SETTINGS"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DogeDexDatabase = Room.databaseBuilder(
        context,
        DogeDexDatabase::class.java,
        NAME_DB
    ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile(NAME_SETTINGS) },
        )
    }


}