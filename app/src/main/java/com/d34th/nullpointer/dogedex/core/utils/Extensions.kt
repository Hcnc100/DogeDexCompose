package com.d34th.nullpointer.dogedex.core.utils

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener({
            continuation.resume(future.get())
        }, executor)
    }
}

fun Context.getTmpFile(): File {
    val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
        createNewFile()
        deleteOnExit()
    }
    return tmpFile
}