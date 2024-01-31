package com.d34th.nullpointer.dogedex.ui.screen.details.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
fun HeaderDetailsDogs(
    dogData: DogData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = modifier,
            text = dogData.name,
            style = MaterialTheme.typography.h5
        )
        Text(
            text = stringResource(id = R.string.title_lifeExpectancy, dogData.lifeExpectancy),
            fontSize = 16.sp,
            modifier = modifier,
            style = MaterialTheme.typography.caption,
        )
        Text(
            fontSize = 16.sp,
            text = dogData.temperament,
            modifier = modifier,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.caption
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@SimplePreview
@Composable
private fun HeaderDetailsDogsPreview() {
    HeaderDetailsDogs(
        dogData = DogData.exampleHasDog
    )
}
