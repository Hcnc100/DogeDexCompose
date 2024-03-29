package com.nullpointer.dogedex.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nullpointer.dogedex.data.settings.SettingsDataStore
import com.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        dataStore: DataStore<Preferences>
    ): SettingsDataStore = SettingsDataStore(dataStore)


    @Provides
    @Singleton
    fun provideSettingsLocalDataSource(
        settingsDataStore: SettingsDataStore
    ):SettingsLocalDataSource = SettingsLocalDataSourceImpl(settingsDataStore)
}