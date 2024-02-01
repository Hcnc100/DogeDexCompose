package com.nullpointer.dogedex.ui.screen.login.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.ui.preview.config.SimplePreview
import com.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.nullpointer.dogedex.ui.screen.login.actions.LoginAction
import com.nullpointer.dogedex.ui.screen.login.test.LoginTestTags

@Composable
fun ButtonsSignInAndSignUp(
    isAuthenticating: Boolean,
    onLoginAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        when (isAuthenticating) {
            true -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.semantics { testTag = LoginTestTags.INDICATOR_PROGRESS }
                )
            }
            false -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExtendedFloatingActionButton(
                        modifier = Modifier.width(200.dp),
                        text = { Text(stringResource(R.string.text_button_sign_in)) },
                        onClick = { onLoginAction(LoginAction.LOGIN) },
                    )
                    CreateAccountSection(
                        actionClick = { onLoginAction(LoginAction.REGISTER) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}


@SimplePreview
@Composable
private fun ButtonsSignInAndSignUpPreview(
    @PreviewParameter(BooleanProvider::class)
    isAuthenticating: Boolean
) {
    ButtonsSignInAndSignUp(
        isAuthenticating = isAuthenticating,
        onLoginAction = {}
    )
}
