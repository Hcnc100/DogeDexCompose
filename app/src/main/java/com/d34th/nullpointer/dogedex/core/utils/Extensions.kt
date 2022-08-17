package com.d34th.nullpointer.dogedex.core.utils

import android.content.Context
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)