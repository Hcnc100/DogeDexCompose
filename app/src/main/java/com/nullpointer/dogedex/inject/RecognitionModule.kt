package com.nullpointer.dogedex.inject

import android.content.Context
import com.nullpointer.dogedex.domain.ia.RecognitionRepoImpl
import com.nullpointer.dogedex.domain.ia.RecognitionRepository
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