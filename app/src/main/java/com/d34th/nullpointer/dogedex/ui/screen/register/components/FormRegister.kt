package com.d34th.nullpointer.dogedex.ui.screen.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.d34th.nullpointer.dogedex.ui.screen.register.test.SignUpTestTag
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.d34th.nullpointer.dogedex.ui.share.PasswordTextSavable

@Composable
fun FormRegister(
    isEnable: Boolean,
    modifier: Modifier = Modifier,
    emailProperty: PropertySavableString,
    passwordProperty: PropertySavableString,
    passwordRepeatProperty: PropertySavableString,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        EditableTextSavable(
            singleLine = true,
            isEnabled = isEnable,
            valueProperty = emailProperty,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                autoCorrect = false
            ),
            modifierText = Modifier.semantics { testTag = SignUpTestTag.INPUT_EMAIL }
        )
        PasswordTextSavable(
            singleLine = true,
            isEnabled = isEnable,
            valueProperty = passwordProperty,
            modifierText = Modifier.semantics { testTag = SignUpTestTag.INPUT_PASSWORD },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
        )
        PasswordTextSavable(
            singleLine = true,
            isEnabled = isEnable,
            valueProperty = passwordRepeatProperty,
            modifierText = Modifier.semantics {
                testTag = SignUpTestTag.INPUT_PASSWORD_CONFIRM
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )
    }
}

@Preview
@Composable
private fun FormRegisterPreview(
    @PreviewParameter(BooleanProvider::class)
    isEnable: Boolean
) {
    FormRegister(
        isEnable = isEnable,
        emailProperty = PropertySavableString.example,
        passwordProperty = PropertySavableString.example,
        passwordRepeatProperty = PropertySavableString.example,
    )
}