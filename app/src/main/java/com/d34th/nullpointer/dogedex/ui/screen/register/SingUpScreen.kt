package com.d34th.nullpointer.dogedex.ui.screen.register

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.ui.preview.config.OrientationPreviews
import com.d34th.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.d34th.nullpointer.dogedex.ui.screen.register.actions.RegisterActions
import com.d34th.nullpointer.dogedex.ui.screen.register.actions.RegisterActions.ACTION_BACK
import com.d34th.nullpointer.dogedex.ui.screen.register.actions.RegisterActions.ACTION_NEXT
import com.d34th.nullpointer.dogedex.ui.screen.register.actions.RegisterActions.REGISTER
import com.d34th.nullpointer.dogedex.ui.screen.register.components.ButtonProgressRegister
import com.d34th.nullpointer.dogedex.ui.screen.register.components.FormRegister
import com.d34th.nullpointer.dogedex.ui.screen.register.viewModel.SignUpViewModel
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.d34th.nullpointer.dogedex.ui.states.FieldsScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberFieldsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    signUpState: FieldsScreenState = rememberFieldsScreenState()
) {

    LaunchedEffect(key1 = Unit) {
        signUpViewModel.messageSignUp.collect(signUpState::showSnackMessage)
    }

    SignUpScreen(
        scaffoldState = signUpState.scaffoldState,
        isAuthenticating = signUpViewModel.isAuthenticating,
        emailProperty = signUpViewModel.emailUser,
        passwordProperty = signUpViewModel.passwordUser,
        passwordRepeatProperty = signUpViewModel.passwordRepeatUser,
        onRegisterActions = { registerActions ->
            when (registerActions) {
                ACTION_BACK -> navigator.popBackStack()
                ACTION_NEXT -> signUpState.downAnotherField()
                REGISTER -> {
                    signUpState.clearFocus()
                    signUpViewModel.getDataValid()?.let {
                        signUpViewModel.signUp(it)
                    }
                }
            }
        }
    )
}


@Composable
fun SignUpScreen(
    scaffoldState: ScaffoldState,
    onRegisterActions: (RegisterActions) -> Unit,
    isAuthenticating: Boolean,
    emailProperty: PropertySavableString,
    passwordProperty: PropertySavableString,
    passwordRepeatProperty: PropertySavableString,
    config: Configuration = LocalConfiguration.current
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ToolbarBack(
                title = stringResource(R.string.title_sign_up),
                actionBack = { onRegisterActions(ACTION_BACK) }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height((config.screenHeightDp.dp - 64.dp))
                    .padding(vertical = 40.dp)
            ) {
                FormRegister(
                    emailProperty = emailProperty,
                    isEnable = !isAuthenticating,
                    passwordProperty = passwordProperty,
                    passwordRepeatProperty = passwordRepeatProperty,
                    modifier = Modifier.widthIn(min = 250.dp, max = 300.dp)
                )
                ButtonProgressRegister(
                    isAuthenticating = isAuthenticating,
                    actionClickAuth = { onRegisterActions(REGISTER) }
                )
            }
        }
    }
}

@OrientationPreviews
@Composable
private fun SignUpScreenPreview(
    @PreviewParameter(BooleanProvider::class)
    isAuthenticating: Boolean
) {
    SignUpScreen(
        onRegisterActions = {},
        isAuthenticating = isAuthenticating,
        scaffoldState = rememberScaffoldState(),
        emailProperty = PropertySavableString.example,
        passwordProperty = PropertySavableString.example,
        passwordRepeatProperty = PropertySavableString.example,
    )
}
