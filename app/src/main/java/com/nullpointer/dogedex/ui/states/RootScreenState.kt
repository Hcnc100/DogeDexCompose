package com.nullpointer.dogedex.ui.states

import android.content.Context
import android.net.Uri
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nullpointer.dogedex.ui.interfaces.ActionRootDestinations
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction

class RootScreenState(
    scaffoldState: ScaffoldState,
    context: Context,
    val navHostController: NavHostController
) : SimpleScreenState(scaffoldState, context) {
    val actionRootDestinations = object : ActionRootDestinations {
        override fun backDestination(): Boolean {
            return navHostController.popBackStack()
        }

        override fun changeRoot(direction: Direction) {
            navHostController.navigate(direction)
        }

        override fun changeRoot(route: Uri) {
            navHostController.navigate(route)
        }


    }
}

@Composable
fun rememberRootScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navHostController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(scaffoldState, navHostController) {
    RootScreenState(scaffoldState, context, navHostController)
}