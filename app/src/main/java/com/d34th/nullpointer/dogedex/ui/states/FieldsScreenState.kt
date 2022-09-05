package com.d34th.nullpointer.dogedex.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

class FieldsScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    private val focusManager: FocusManager
) : SimpleScreenState(scaffoldState, context) {

    fun downAnotherField() {
        focusManager.moveFocus(FocusDirection.Down)
    }

    fun clearFocus() {
        focusManager.clearFocus()
    }
}


@Composable
fun rememberFieldsScreenState(
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) = remember(scaffoldState) {
    FieldsScreenState(context, scaffoldState, focusManager)
}