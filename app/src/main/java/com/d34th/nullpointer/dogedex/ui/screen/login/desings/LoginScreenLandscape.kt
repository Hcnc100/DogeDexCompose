package com.d34th.nullpointer.dogedex.ui.screen.login.desings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.ui.preview.config.LandscapePreview
import com.d34th.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.d34th.nullpointer.dogedex.ui.screen.login.actions.LoginAction
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.ButtonsSignInAndSignUp
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.FormLogin
import com.d34th.nullpointer.dogedex.ui.screen.login.componets.LogoApp


@Composable
fun LoginScreenLandscape(
    isAuthenticating: Boolean,
    modifier: Modifier = Modifier,
    emailValue: PropertySavableString,
    passwordValue: PropertySavableString,
    onLoginAction: (LoginAction) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Column(
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            LogoApp()
            ButtonsSignInAndSignUp(
                onLoginAction = onLoginAction,
                isAuthenticating = isAuthenticating
            )
        }
        Box(
            modifier = Modifier.weight(0.6f),
            contentAlignment = Alignment.Center
        ){
            FormLogin(
                emailValue = emailValue,
                passwordValue = passwordValue,
                isEnableFields = isAuthenticating,
                signInAction = { onLoginAction(LoginAction.LOGIN) },
                modifier = Modifier.widthIn(min = 250.dp, max = 300.dp)
            )
        }
    }
}

@LandscapePreview
@Composable
private fun LoginScreenLandscapePreview(
    @PreviewParameter(BooleanProvider::class) isAuthenticating: Boolean
) {
    LoginScreenLandscape(
        emailValue = PropertySavableString.example,
        passwordValue = PropertySavableString.example,
        isAuthenticating = isAuthenticating,
        onLoginAction = {},
        modifier = Modifier.background(MaterialTheme.colors.primary)
    )
}