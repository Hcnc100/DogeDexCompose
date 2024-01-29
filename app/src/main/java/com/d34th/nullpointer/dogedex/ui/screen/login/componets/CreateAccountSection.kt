package com.d34th.nullpointer.dogedex.ui.screen.login.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
 fun CreateAccountSection(
    actionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.text_has_not_account),
            modifier = Modifier.alignByBaseline()
        )
        OutlinedButton(
            onClick = actionClick,
            modifier = Modifier.alignByBaseline()
        ) {
            Text(
                text = stringResource(R.string.text_button_go_sign_up)
            )
        }
    }
}


@SimplePreview
@Composable
private fun CreateAccountSectionPreview() {
    CreateAccountSection(actionClick = {})
}
