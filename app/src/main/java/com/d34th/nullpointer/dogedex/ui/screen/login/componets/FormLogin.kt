package com.d34th.nullpointer.dogedex.ui.screen.login.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.ui.screen.login.test.LoginTestTags
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.d34th.nullpointer.dogedex.ui.share.PasswordTextSavable

@Composable
fun FormLogin(
    isEnableFields: Boolean,
    signInAction: () -> Unit,
    emailValue: PropertySavableString,
    passwordValue: PropertySavableString,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ContainerFieldAuth {
            EditableTextSavable(
                singleLine = true,
                isEnabled = !isEnableFields,
                valueProperty = emailValue,
                modifier = Modifier.padding(10.dp),
                modifierText = Modifier.semantics { testTag = LoginTestTags.INPUT_EMAIL },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
            )
        }

        ContainerFieldAuth {
            PasswordTextSavable(
                singleLine = true,
                isEnabled = !isEnableFields,
                valueProperty = passwordValue,
                modifier = Modifier.padding(10.dp),
                modifierText = Modifier.semantics { testTag = LoginTestTags.INPUT_PASSWORD },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { signInAction() }
                )
            )
        }
    }
}

@Preview
@Composable
fun FormLoginPreview() {
    FormLogin(
        isEnableFields = false,
        signInAction = {},
        emailValue = PropertySavableString.example,
        passwordValue = PropertySavableString.example
    )
}

@Composable
private fun ContainerFieldAuth(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.background),
        content = content
    )
}

