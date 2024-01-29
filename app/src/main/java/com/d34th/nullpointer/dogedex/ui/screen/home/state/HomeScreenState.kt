package com.d34th.nullpointer.dogedex.ui.screen.home.state

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState

@Stable
class HomeScreenState(
    val navController: NavHostController,
    scaffoldState: ScaffoldState,
    context: Context
) : SimpleScreenState(scaffoldState, context)


@Composable
fun rememberHomeScreenState(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    context: Context = LocalContext.current
) = remember(navController, scaffoldState) {
    HomeScreenState(
        context = context,
        navController = navController,
        scaffoldState = scaffoldState,
    )
}