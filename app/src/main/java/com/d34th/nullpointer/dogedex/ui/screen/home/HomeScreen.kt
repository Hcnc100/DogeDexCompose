package com.d34th.nullpointer.dogedex.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.d34th.nullpointer.dogedex.ui.screen.NavGraphs
import com.d34th.nullpointer.dogedex.ui.screen.home.components.HomeBottomBar
import com.d34th.nullpointer.dogedex.ui.screen.home.state.HomeScreenState
import com.d34th.nullpointer.dogedex.ui.screen.home.state.rememberHomeScreenState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph


@RootNavGraph
@Destination
@Composable
fun HomeScreen(
    homeScreenState: HomeScreenState = rememberHomeScreenState()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Dogedex")
            })
        },
        bottomBar = {
            HomeBottomBar(
                navController = homeScreenState.navController
            )
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.home,
            modifier = Modifier.padding(it),
            navController = homeScreenState.navController,
        )
    }
}