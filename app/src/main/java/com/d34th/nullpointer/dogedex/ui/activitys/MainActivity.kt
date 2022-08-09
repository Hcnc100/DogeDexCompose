 package com.d34th.nullpointer.dogedex.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.d34th.nullpointer.dogedex.ui.screen.NavGraphs
import com.d34th.nullpointer.dogedex.ui.states.rememberRootScreenState
import com.d34th.nullpointer.dogedex.ui.theme.DogedexTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val rootState = rememberRootScreenState()
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        startRoute = NavGraphs.root.startRoute,
                        navController = rootState.navHostController
                    )
                }
            }
        }
    }
}
