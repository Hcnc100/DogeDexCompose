package com.d34th.nullpointer.dogedex.inject

import android.content.Context
import com.d34th.nullpointer.dogedex.data.local.prefereneces.PreferencesDataSource
import com.d34th.nullpointer.dogedex.data.local.prefereneces.PreferencesDataSourceImpl
import com.d34th.nullpointer.dogedex.data.local.prefereneces.PreferencesUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context,
    ): PreferencesUser = PreferencesUser(context)

    @Provides
    @Singleton
    fun providePreferencesDataSource(
        preferencesUser: PreferencesUser
    ): PreferencesDataSource = PreferencesDataSourceImpl(preferencesUser)
}