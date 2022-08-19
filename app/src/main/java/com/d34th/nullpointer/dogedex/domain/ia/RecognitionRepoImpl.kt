package com.d34th.nullpointer.dogedex.domain.ia

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.d34th.nullpointer.dogedex.core.utils.getCameraProvider
import com.d34th.nullpointer.dogedex.core.utils.toBitmap
import com.d34th.nullpointer.dogedex.ia.Classifier
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import org.tensorflow.lite.support.common.FileUtil
import timber.log.Timber
import java.util.concurrent.Executors

class RecognitionRepoImpl(
    private val context: Context
) : RecognitionRepository {

    companion object {
        private const val NAME_DOG_MODEL_ASSET = "model.tflite"
        private const val NAME_LABELS_MODEL_ASSET = "labels.txt"
    }

    private val executor = Executors.newSingleThreadExecutor()
    private val previewUseCase: Preview = Preview.Builder().build()
    private val currentCameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val imageAnalysis: ImageAnalysis by lazy { createImageAnalysis() }

    private fun createImageAnalysis(): ImageAnalysis {
        // * only get last image
        return ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()
    }

    // * ia
    private val classifier: Classifier by lazy {
        Classifier(
            FileUtil.loadMappedFile(context, NAME_DOG_MODEL_ASSET),
            FileUtil.loadLabels(context, NAME_LABELS_MODEL_ASSET)
        )
    }

    override suspend fun bindCameraToUseCases(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        callbackRecognizeDog: (dog: DogRecognition, isConfidence: Boolean) -> Unit
    ) {
        imageAnalysis.setAnalyzer(executor) { analyzer ->
            analyzer.toBitmap()?.let { bitmap ->
                classifier.recognizeImage(bitmap).firstOrNull()?.let {
                    callbackRecognizeDog(it, it.confidence > 70f)
                }
            }
            analyzer.close()
        }
        // * bind view preview to preview use case
        previewUseCase.setSurfaceProvider(previewView.surfaceProvider)
        val cameraProvider = context.getCameraProvider()
        try {
            // * Must unbind the use-cases before rebinding them.
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                currentCameraSelector,
                previewUseCase,
                imageAnalysis
            )
        } catch (ex: Exception) {
            Timber.d("Use case binding failed $ex")
        }
    }

    override fun clearCamera() {
        executor.shutdown()
    }
}