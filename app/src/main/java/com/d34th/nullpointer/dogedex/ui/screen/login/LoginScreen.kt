package com.d34th.nullpointer.dogedex.ui.screen.login

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.preview.config.OrientationPreviews
import com.d34th.nullpointer.dogedex.ui.screen.destinations.LoginScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SignUpScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.login.actions.LoginAction
import com.d34th.nullpointer.dogedex.ui.screen.login.actions.LoginAction.*
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.ButtonsSignInAndSignUp
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.FormLogin
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.LogoApp
import com.d34th.nullpointer.dogedex.ui.screen.login.desings.LoginScreenLandscape
import com.d34th.nullpointer.dogedex.ui.screen.login.desings.LoginScreenPortrait
import com.d34th.nullpointer.dogedex.ui.screen.login.viewModel.LoginViewModel
import com.d34th.nullpointer.dogedex.ui.states.FieldsScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberFieldsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.merge

@Destination(start = true)
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





