package com.d34th.nullpointer.dogedex.ui.screen.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.ui.share.DogImg


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDogsSuccess(
    listDog: List<Dog>,
    clickDetails: (Dog) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.size_item_card_dog))
    ) {
        items(listDog, key = { it.id }) { dog ->
            ItemDog(dog = dog,
                modifier = Modifier.animateItemPlacement(),
                actionClick = {
                    clickDetails(dog)
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemDog(dog: Dog, actionClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(2.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item)),
        onClick = actionClick
    ) {
        Column(
            modifier = modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DogImg(
                urlImg = dog.imgUrl,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_img_dog_item))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = dog.name)
        }
    }
}