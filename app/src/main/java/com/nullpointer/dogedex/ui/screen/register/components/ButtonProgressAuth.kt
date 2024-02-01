package com.nullpointer.dogedex.ui.screen.register.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.nullpointer.dogedex.ui.screen.register.test.SignUpTestTag


@Composable
fun ButtonProgressRegister(
    isAuthenticating: Boolean,
    actionClickAuth: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isAuthenticating) {
        CircularProgressIndicator(
            modifier = modifier.semantics { testTag = SignUpTestTag.INDICATOR_PROGRESS }
        )
    } else {
        ExtendedFloatingActionButton(
            modifier = modifier,
            onClick = actionClickAuth,
            text = { Text(stringResource(R.string.text_button_next_sign_up)) }
        )
    }
}


@Preview
@Composable
private fun ButtonProgressRegisterPreview(
    @PreviewParameter(BooleanProvider::class)
    isAuthenticating: Boolean
) {
    ButtonProgressRegister(
        actionClickAuth = {},
        isAuthenticating = isAuthenticating,
    )
}