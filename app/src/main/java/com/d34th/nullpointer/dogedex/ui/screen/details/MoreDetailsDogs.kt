package com.d34th.nullpointer.dogedex.ui.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData

@Composable
fun MoreDetailsDogs(dogData: DogData, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
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

        GroupDogDetails(dogData = dogData)

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

@Composable
fun GroupDogDetails(dogData: DogData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = dogData.type)
        Text(stringResource(R.string.title_dog_group))
    }
}

@Composable
fun GenderDetailsDog(
    dogWeight: String,
    dogHeight: String,
    gender: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gender,
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(10.dp))

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
