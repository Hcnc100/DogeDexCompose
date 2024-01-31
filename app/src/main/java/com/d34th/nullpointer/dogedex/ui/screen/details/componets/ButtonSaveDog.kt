package com.d34th.nullpointer.dogedex.ui.screen.details.componets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview
import com.d34th.nullpointer.dogedex.ui.preview.provider.BooleanProvider

@Composable
fun ButtonSaveDog(
    isVisible: Boolean,
    actionBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = isVisible) {
        ExtendedFloatingActionButton(
            modifier = modifier.padding(horizontal = 20.dp),
            onClick = actionBack,
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = stringResource(R.string.text_save_dog))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_save),
                        contentDescription = stringResource(R.string.description_button_save_dog)
                    )
                }
            }
        )
    }
}

@SimplePreview
@Composable
private fun ButtonSaveDogPreview(
    @PreviewParameter(BooleanProvider::class)
    isVisible: Boolean

) {
    ButtonSaveDog(
        actionBack = {},
        isVisible = isVisible
    )
}
