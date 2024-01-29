package com.d34th.nullpointer.dogedex.ui.screen.login.desings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview
import com.d34th.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.d34th.nullpointer.dogedex.ui.screen.login.actions.LoginAction
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.ButtonsSignInAndSignUp
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.FormLogin
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.LogoApp

@Composable
fun LoginScreenPortrait(
    modifier: Modifier = Modifier,
    emailValue: PropertySavableString,
    passwordValue: PropertySavableString,
    isAuthenticating: Boolean,
    onLoginAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoApp(
            modifier = Modifier
                .weight(.33f)
                .wrapContentSize()
        )
        Column(
            modifier = Modifier
                .weight(.66f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FormLogin(
                modifier = Modifier.widthIn(min = 250.dp, max = 300.dp),
                emailValue = emailValue,
                passwordValue = passwordValue,
                isEnableFields = isAuthenticating,
                signInAction = { onLoginAction(LoginAction.LOGIN) }
            )

            ButtonsSignInAndSignUp(
                onLoginAction = onLoginAction,
                isAuthenticating = isAuthenticating
            )
        }
    }
}

@SimplePreview
@Composable
private fun LoginScreenPortraitPreview(
    @PreviewParameter(BooleanProvider::class)
    isAuthenticating: Boolean
) {
    LoginScreenPortrait(
        emailValue = PropertySavableString.example,
        passwordValue = PropertySavableString.example,
        isAuthenticating = isAuthenticating,
        onLoginAction = {}
    )
}