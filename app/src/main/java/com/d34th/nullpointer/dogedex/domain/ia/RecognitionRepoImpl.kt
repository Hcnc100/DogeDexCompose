package com.d34th.nullpointer.dogedex.domain.ia

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import com.d34th.nullpointer.dogedex.ia.Classifier
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import org.tensorflow.lite.support.common.FileUtil
import java.util.concurrent.Executors

class RecognitionRepoImpl(
    private val context: Context
) : RecognitionRepository {

    companion object {
        private const val NAME_DOG_MODEL_ASSET = "model.tflite"
        private const val NAME_LABELS_MODEL_ASSET = "labels.txt"
    }

    private val executor = Executors.newSingleThreadExecutor()

    // * ia
    private val classifier: Classifier by lazy {
        Classifier(
            FileUtil.loadMappedFile(context, NAME_DOG_MODEL_ASSET),
            FileUtil.loadLabels(context, NAME_LABELS_MODEL_ASSET)
        )
    }

    override fun bindAnalyzeImage(
        cameraController: LifecycleCameraController,
        callbackRecognizeDog: (dog: DogRecognition, isConfidence: Boolean) -> Unit
    ) {
        // * This values is for default
        //cameraController.imageAnalysisBackpressureStrategy = ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
        cameraController.setImageAnalysisAnalyzer(executor) { analyzer ->
            analyzer.toBitmap().let { bitmap ->
                classifier.recognizeImage(bitmap).firstOrNull()?.let {
                    callbackRecognizeDog(it, it.confidence > 70f)
                }
            }
            analyzer.close()
        }
    }


    override fun clearCamera() {
        executor.shutdown()
    }
}