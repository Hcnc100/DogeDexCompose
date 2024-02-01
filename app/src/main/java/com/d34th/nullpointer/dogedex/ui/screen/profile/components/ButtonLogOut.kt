package com.d34th.nullpointer.dogedex.ui.screen.profile.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ui.preview.provider.BooleanProvider

@Composable
fun ButtonLogOut(
    action: () -> Unit,
    isProcessing: Boolean
) {
    when (isProcessing) {
        true -> CircularProgressIndicator()
        false -> ExtendedFloatingActionButton(
            onClick = action,
            modifier = Modifier.width(300.dp),
            text = { Text(text = stringResource(R.string.text_button_sign_out)) }
        )
    }
}


@Preview
@Composable
private fun ButtonLogOutPreview(
    @PreviewParameter(BooleanProvider::class)
    isProcessing: Boolean
) {
    ButtonLogOut(
        action = {},
        isProcessing = isProcessing
    )
}