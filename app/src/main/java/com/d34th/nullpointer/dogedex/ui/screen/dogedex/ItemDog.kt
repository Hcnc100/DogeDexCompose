package com.d34th.nullpointer.dogedex.ui.screen.dogedex

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.ui.share.DogImg

@Composable
fun ItemDog(dog: Dog, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(2.dp), shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item))) {
        Column(modifier = modifier.padding(10.dp)) {
            DogImg(urlImg = dog.imgUrl, modifier = Modifier.size(dimensionResource(id = R.dimen.size_img_dog_item)))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = dog.name)
        }
    }
}