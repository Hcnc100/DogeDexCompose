package com.d34th.nullpointer.dogedex.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class DogsScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val swipeRefreshState: SwipeRefreshState
) : SimpleScreenState(scaffoldState, context) {

    val isRefreshing get() = swipeRefreshState.isRefreshing

}

@Composable
fun rememberDogsScreenState(
    isRefreshing: Boolean,
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing)
) = remember(scaffoldState) {
    DogsScreenState(context, scaffoldState, swipeRefreshState)
}