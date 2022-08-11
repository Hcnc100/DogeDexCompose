package com.d34th.nullpointer.dogedex.ui.states

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

open class SimpleScreenState(
    val scaffoldState: ScaffoldState,
    val context: Context
) {
    suspend fun showSnackMessage(@StringRes stringRes: Int) {
        scaffoldState.snackbarHostState.showSnackbar(
            context.getString(stringRes)
        )
    }

    suspend fun showSnackMessage(message:String) {
        scaffoldState.snackbarHostState.showSnackbar(
           message
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