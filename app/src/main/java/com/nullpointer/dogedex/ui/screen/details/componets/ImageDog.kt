package com.nullpointer.dogedex.ui.screen.details.componets

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.ui.share.AsyncImageFade

@Composable
fun ImageDog(
    dogData: DogData,
    modifier: Modifier = Modifier
) {
    AsyncImageFade(
        data = dogData.imgUrl,
        contentDescription = stringResource(
            R.string.description_has_dog,
            dogData.id,
            dogData.name
        ),
        modifier = modifier.size(150.dp)
    )

}

