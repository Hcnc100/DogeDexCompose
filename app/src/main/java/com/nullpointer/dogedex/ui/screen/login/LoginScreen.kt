package com.nullpointer.dogedex.ui.screen.login

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.dogedex.core.delegate.PropertySavableString
import com.nullpointer.dogedex.ui.preview.config.OrientationPreviews
import com.nullpointer.dogedex.ui.screen.destinations.SignUpScreenDestination
import com.nullpointer.dogedex.ui.screen.login.actions.LoginAction
import com.nullpointer.dogedex.ui.screen.login.actions.LoginAction.LOGIN
import com.nullpointer.dogedex.ui.screen.login.actions.LoginAction.REGISTER
import com.nullpointer.dogedex.ui.screen.login.desings.LoginScreenLandscape
import com.nullpointer.dogedex.ui.screen.login.desings.LoginScreenPortrait
import com.nullpointer.dogedex.ui.screen.login.viewModel.LoginViewModel
import com.nullpointer.dogedex.ui.states.FieldsScreenState
import com.nullpointer.dogedex.ui.states.rememberFieldsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel = hiltViewModel(),
    loginScreenState: FieldsScreenState = rememberFieldsScreenState(),
) {

    LaunchedEffect(key1 = Unit) {
        loginViewModel.messageLogin.collect(loginScreenState::showSnackMessage)
    }

    LoginScreen(
        scaffoldState = loginScreenState.scaffoldState,
        emailValue = loginViewModel.emailLogin,
        passwordValue = loginViewModel.passwordLogin,
        isAuthenticating = loginViewModel.isAuthenticating,
        onLoginAction = { loginAction ->
            when (loginAction) {
                LOGIN -> {
                    loginScreenState.clearFocus()
                    loginViewModel.getCredentialAndValidate()?.let {
                        loginViewModel.signIn(it)
                    }
                }
                REGISTER -> navigator.navigate(SignUpScreenDestination)
            }
        }
    )
}


@Composable
fun LoginScreen(
    isAuthenticating: Boolean,
    scaffoldState: ScaffoldState,
    emailValue: PropertySavableString,
    passwordValue: PropertySavableString,
    onLoginAction: (LoginAction) -> Unit,
    configuration: Configuration = LocalConfiguration.current
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.primary
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            when (configuration.orientation) {
                ORIENTATION_PORTRAIT -> {
                    LoginScreenPortrait(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(configuration.screenHeightDp.dp),
                        emailValue = emailValue,
                        passwordValue = passwordValue,
                        isAuthenticating = isAuthenticating,
                        onLoginAction = onLoginAction
                    )
                }

                else -> {
                    LoginScreenLandscape(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(configuration.screenHeightDp.dp),
                        emailValue = emailValue,
                        passwordValue = passwordValue,
                        isAuthenticating = isAuthenticating,
                        onLoginAction = onLoginAction
                    )
                }
            }
        }
    }
}

@OrientationPreviews
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onLoginAction = {},
        isAuthenticating = false,
        scaffoldState = rememberScaffoldState(),
        emailValue = PropertySavableString.example,
        passwordValue = PropertySavableString.example
    )
}





