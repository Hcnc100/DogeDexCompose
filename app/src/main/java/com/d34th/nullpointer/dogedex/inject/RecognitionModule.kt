package com.d34th.nullpointer.dogedex.inject

import android.content.Context
import com.d34th.nullpointer.dogedex.domain.ia.RecognitionRepoImpl
import com.d34th.nullpointer.dogedex.domain.ia.RecognitionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecognitionModule {

    @Provides
    @Singleton
    fun getRecognitionRepo(
        @ApplicationContext context: Context
    ): RecognitionRepository = RecognitionRepoImpl(context)
}