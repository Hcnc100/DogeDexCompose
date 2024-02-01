package com.nullpointer.dogedex.ui.states

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

open class SimpleScreenState(
    val scaffoldState: ScaffoldState,
    val context: Context
) {
    suspend fun showSnackMessage(@StringRes stringRes: Int) {
        scaffoldState.snackbarHostState.showSnackbar(
            context.getString(stringRes)
        )
    }
}

@Composable
fun rememberSimpleScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    context: Context = LocalContext.current,
) = remember(scaffoldState) {
    SimpleScreenState(scaffoldState, context)
}