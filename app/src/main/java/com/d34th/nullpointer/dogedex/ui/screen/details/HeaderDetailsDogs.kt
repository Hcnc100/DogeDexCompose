package com.d34th.nullpointer.dogedex.ui.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData

@Composable
fun HeaderDetailsDogs(
    dogData: DogData,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        NameDog(name = dogData.name, modifier = Modifier)
        Spacer(modifier = Modifier.size(10.dp))
        AgeDog(age = dogData.lifeExpectancy)
        Spacer(modifier = Modifier.size(5.dp))
        TemperamentDog(temperament = dogData.temperament)
        Divider(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun NameDog(name: String, modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = name, style = MaterialTheme.typography.h5)
}

@Composable
private fun AgeDog(age: String, modifier: Modifier = Modifier) {
    Text(
        text = age,
        modifier = modifier,
        style = MaterialTheme.typography.caption,
        fontSize = 16.sp
    )
}

@Composable
fun TemperamentDog(temperament: String, modifier: Modifier = Modifier) {
    Text(
        text = temperament,
        modifier = modifier,
        style = MaterialTheme.typography.caption,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    )
}