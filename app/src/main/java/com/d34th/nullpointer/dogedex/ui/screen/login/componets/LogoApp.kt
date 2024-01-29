package com.d34th.nullpointer.dogedex.ui.screen.login.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.d34th.nullpointer.dogedex.R

@Composable
 fun LogoApp(
    modifier: Modifier = Modifier
) {

        Card(
            modifier = modifier.size(150.dp),
            shape = CircleShape
        ) {
            AsyncImage(
                model = R.drawable.ic_dog,
                modifier = Modifier.padding(20.dp).fillMaxSize(),
                contentDescription = stringResource(id = R.string.app_name),
            )
        }

}


@Preview
@Composable
fun LogoAppPreview() {
    LogoApp()
}