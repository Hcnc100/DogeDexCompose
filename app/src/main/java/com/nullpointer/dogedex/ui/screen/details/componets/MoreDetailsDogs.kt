package com.nullpointer.dogedex.ui.screen.details.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.dp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
fun MoreDetailsDogs(
    dogData: DogData,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GenderDetailsDog(
            gender = stringResource(R.string.title_gender_female),
            dogWeight = dogData.weightFemale,
            dogHeight = dogData.heightFemale.toString()
        )

        Divider(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .width(1.dp)
        )

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = dogData.type)
            Text(
                stringResource(R.string.title_dog_group),
                style = MaterialTheme.typography.caption
            )
        }

        Divider(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .width(1.dp)
        )

        GenderDetailsDog(
            gender = stringResource(R.string.title_gender_male),
            dogWeight = dogData.weightMale,
            dogHeight = dogData.heightMale.toString()
        )
    }
}

@SimplePreview
@Composable
private fun MoreDetailsDogsPreview() {
    MoreDetailsDogs(
        dogData = DogData.exampleHasDog
    )
}

