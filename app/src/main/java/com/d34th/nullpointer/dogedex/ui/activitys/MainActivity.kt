 package com.d34th.nullpointer.dogedex.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.d34th.nullpointer.dogedex.core.states.AuthState
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.screen.NavGraphs
import com.d34th.nullpointer.dogedex.ui.screen.destinations.CameraScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.LoginScreenDestination
import com.d34th.nullpointer.dogedex.ui.states.rememberRootScreenState
import com.d34th.nullpointer.dogedex.ui.theme.DogedexTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {
     private val authViewModel: AuthViewModel by viewModels()

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         var isSplash = true
         installSplashScreen().apply {
             setKeepOnScreenCondition {
                 isSplash
             }
         }
         setContent {
             DogedexTheme {
                 // A surface container using the 'background' color from the theme
                 Surface(
                     modifier = Modifier.fillMaxSize(),
                     color = MaterialTheme.colors.background
                 ) {
                     val rootState = rememberRootScreenState()
                     val isAuthUser by authViewModel.stateUser.collectAsState()
                     when (isAuthUser) {
                         is AuthState.Authenticated -> CameraScreenDestination
                         AuthState.Unauthenticated -> LoginScreenDestination
                         AuthState.Authenticating -> null
                     }?.let {
                         isSplash = false
                         DestinationsNavHost(
                             navGraph = NavGraphs.root,
                             startRoute = it,
                             navController = rootState.navHostController,
                             dependenciesContainerBuilder = {
                                 dependency(authViewModel)
                             }
                         )
                     }

                 }
             }
         }
     }
 }
