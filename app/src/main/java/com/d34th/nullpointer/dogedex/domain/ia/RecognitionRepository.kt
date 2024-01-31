package com.d34th.nullpointer.dogedex.domain.ia

import androidx.camera.view.LifecycleCameraController
import com.d34th.nullpointer.dogedex.ia.DogRecognition

interface RecognitionRepository {
    fun bindAnalyzeImage(
        cameraController: LifecycleCameraController,
        callbackRecognizeDog: (dog: DogRecognition, isConfidence: Boolean) -> Unit
    )

    fun clearCamera()
}
