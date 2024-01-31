package com.d34th.nullpointer.dogedex.ui.screen.details.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
fun GenderDetailsDog(
    dogWeight: String,
    dogHeight: String,
    gender: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gender,
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        Text(
            text = dogWeight,
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W700,
            fontSize = 15.sp
        )
        Text(
            text = stringResource(R.string.title_weight_dog),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W300,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = dogHeight,
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W700,
            fontSize = 15.sp
        )
        Text(
            text = stringResource(R.string.title_height_dog),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W300,
            fontSize = 15.sp
        )
    }
}

@SimplePreview
@Composable
private fun GenderDetailsDogPreview() {
    GenderDetailsDog(
        dogHeight = "12",
        gender = "Female",
        dogWeight = "12"
    )
}