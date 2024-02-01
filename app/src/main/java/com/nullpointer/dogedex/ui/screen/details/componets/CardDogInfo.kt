package com.nullpointer.dogedex.ui.screen.details.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
fun CardDogInfo(
    dogData: DogData,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier.padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.W500,
                text = stringResource(id = R.string.index_dog, dogData.id),
                style = MaterialTheme.typography.caption
            )
            HeaderDetailsDogs(dogData = dogData)
            MoreDetailsDogs(dogData = dogData)
        }
    }
}

@SimplePreview
@Composable
private fun CardDogInfoPreview() {
    CardDogInfo(
        dogData = DogData.exampleHasDog
    )
}