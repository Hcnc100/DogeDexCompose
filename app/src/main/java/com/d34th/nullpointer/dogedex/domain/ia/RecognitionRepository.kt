package com.d34th.nullpointer.dogedex.domain.ia

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.d34th.nullpointer.dogedex.ia.DogRecognition

interface RecognitionRepository {
    suspend fun bindCameraToUseCases(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        callbackRecognizeDog: (dog: DogRecognition, isConfidence: Boolean) -> Unit
    )

    fun clearCamera()
}
