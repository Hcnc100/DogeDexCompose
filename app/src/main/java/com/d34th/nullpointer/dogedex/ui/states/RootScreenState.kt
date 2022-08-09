package com.d34th.nullpointer.dogedex.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class RootScreenState(
    scaffoldState: ScaffoldState,
    context: Context,
    val navHostController: NavHostController
) : SimpleScreenState(scaffoldState, context)

@Composable
fun rememberRootScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navHostController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(scaffoldState, navHostController) {
    RootScreenState(scaffoldState, context, navHostController)
}