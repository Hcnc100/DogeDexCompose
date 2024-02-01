package com.nullpointer.dogedex.ui.screen.listDogs.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.ui.share.AsyncImageFade


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemDog(
    dogData: DogData,
    actionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.size_item_card_dog)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item)),
        onClick = actionClick
    ) {
        Box(
            modifier = modifier.padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (dogData.hasDog) {
                AsyncImageFade(
                    data = dogData.imgUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(
                        R.string.description_has_dog,
                        dogData.id,
                        dogData.name
                    ),
                )
            } else {
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    style = MaterialTheme.typography.body1,
                    text = stringResource(id = R.string.index_dog, dogData.id)
                )
            }
        }
    }
}


@Preview
@Composable
private fun ItemDogPreview() {
    ItemDog(
        dogData = DogData.exampleHasDog,
        actionClick = { /*TODO*/ }
    )
}