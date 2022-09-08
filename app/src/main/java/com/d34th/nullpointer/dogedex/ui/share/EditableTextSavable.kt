package com.d34th.nullpointer.dogedex.ui.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString


@Composable
fun EditableTextSavable(
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    isEnabled: Boolean = true,
    singleLine: Boolean = false,
    valueProperty: PropertySavableString,
    shape: Shape = MaterialTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Surface {
        Column(modifier = modifier.fillMaxWidth()) {
            OutlinedTextField(
                enabled = isEnabled,
                label = { Text(stringResource(id = valueProperty.label)) },
                placeholder = { Text(stringResource(id = valueProperty.hint)) },
                value = valueProperty.value,
                onValueChange = valueProperty::changeValue,
                isError = valueProperty.hasError,
                modifier = modifierText.fillMaxWidth(),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                shape = shape,
                visualTransformation = visualTransformation,
                singleLine = singleLine,
            )
            Row {
                Text(
                    text = if (valueProperty.hasError) stringResource(id = valueProperty.errorValue) else "",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.weight(.9f)
                )
                Text(
                    text = valueProperty.countLength,
                    color = if (valueProperty.hasError) MaterialTheme.colors.error else Color.Unspecified,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
fun PasswordTextSavable(
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    isEnabled: Boolean = true,
    singleLine: Boolean = false,
    valueProperty: PropertySavableString,
    shape: Shape = MaterialTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val (passwordVisible, changeVisible) = rememberSaveable { mutableStateOf(false) }

    val iconAndDescription by remember(passwordVisible) {
        derivedStateOf {
            if (passwordVisible)
                Pair(
                    R.drawable.ic_visibility,
                    R.string.description_show_password
                ) else
                Pair(
                    R.drawable.ic_visibility_off,
                    R.string.description_hide_password
                )
        }
    }

    Surface {
        Column(modifier = modifier.fillMaxWidth()) {
            OutlinedTextField(
                enabled = isEnabled,
                label = { Text(stringResource(id = valueProperty.label)) },
                placeholder = { Text(stringResource(id = valueProperty.hint)) },
                value = valueProperty.value,
                onValueChange = valueProperty::changeValue,
                isError = valueProperty.hasError,
                modifier = modifierText.fillMaxWidth(),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                shape = shape,
                singleLine = singleLine,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val (iconRes, descriptionRes) = iconAndDescription
                    if (valueProperty.value.isNotEmpty())
                        IconButton(onClick = { changeVisible(!passwordVisible) }) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = stringResource(id = descriptionRes)
                            )
                        }
                }
            )
            Row {
                Text(
                    text = if (valueProperty.hasError) stringResource(id = valueProperty.errorValue) else "",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.weight(.9f)
                )
                Text(
                    text = valueProperty.countLength,
                    color = if (valueProperty.hasError) MaterialTheme.colors.error else Color.Unspecified,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}