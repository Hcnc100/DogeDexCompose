package com.d34th.nullpointer.dogedex.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Stable
class DogsScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val pullRefreshState: PullRefreshState
) : SimpleScreenState(scaffoldState, context) {

}

@Composable
fun rememberDogsScreenState(
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    pullRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )
) = remember(
    scaffoldState,
    pullRefreshState
) {
    DogsScreenState(
        context = context,
        scaffoldState = scaffoldState,
        pullRefreshState = pullRefreshState
    )
}