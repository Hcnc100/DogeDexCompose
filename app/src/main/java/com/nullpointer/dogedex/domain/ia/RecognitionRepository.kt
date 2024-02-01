package com.nullpointer.dogedex.domain.ia

import androidx.camera.view.LifecycleCameraController
import com.nullpointer.dogedex.ia.DogRecognition

interface RecognitionRepository {
    fun bindAnalyzeImage(
        cameraController: LifecycleCameraController,
        callbackRecognizeDog: (dog: DogRecognition?) -> Unit
    )

    fun clearCamera()
}
