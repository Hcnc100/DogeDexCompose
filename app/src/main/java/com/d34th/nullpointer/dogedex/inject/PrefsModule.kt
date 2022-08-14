package com.d34th.nullpointer.dogedex.inject

import android.content.Context
import com.d34th.nullpointer.dogedex.data.local.prefs.PrefsUser
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
    ): PrefsUser = PrefsUser(context)
}