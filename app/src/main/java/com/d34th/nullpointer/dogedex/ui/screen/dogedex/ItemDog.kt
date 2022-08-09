package com.d34th.nullpointer.dogedex.ui.screen.dogedex

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.ui.share.DogImg

@Composable
fun ItemDog(dog: Dog, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(2.dp)) {
        Column(modifier = modifier.padding(10.dp)) {
            DogImg(urlImg = dog.imgUrl, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = dog.name)
        }
    }
}