package com.d34th.nullpointer.dogedex.ui.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d34th.nullpointer.dogedex.models.Dog

@Composable
fun MoreDetailsDogs(dog: Dog, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.padding(10.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenderDetailsDog(
            gender = "Female",
            dogWeight = dog.weightFemale,
            dogHeight = dog.heightFemale.toString()
        )

        Divider(
            modifier = Modifier
                .padding(10.dp)
                .height(60.dp)
                .width(1.dp)
        )

        GroupDogDetails(dog = dog)

        Divider(
            modifier = Modifier
                .padding(10.dp)
                .height(60.dp)
                .width(1.dp)
        )

        GenderDetailsDog(
            gender = "Male",
            dogWeight = dog.weightMale,
            dogHeight = dog.heightMale.toString()
        )
    }
}

@Composable
fun GroupDogDetails(dog: Dog, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = dog.type)
        Text("Group")
    }
}

@Composable
fun GenderDetailsDog(
    dogWeight: String,
    dogHeight: String,
    gender: String,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
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
            text = "Weight",
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
            text = "Height",
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W300,
            fontSize = 15.sp
        )
    }
}
