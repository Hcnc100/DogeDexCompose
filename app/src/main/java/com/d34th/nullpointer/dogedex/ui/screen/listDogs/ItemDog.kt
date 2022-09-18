package com.d34th.nullpointer.dogedex.ui.screen.listDogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.ui.share.AsyncImageFade


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
        items(listDog, key = { it.index }) { dog ->
            ItemDog(dog = dog,
                modifier = Modifier.animateItemPlacement(),
                actionClick = { if (dog.hasDog) clickDetails(dog) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemDog(dog: Dog, actionClick: () -> Unit, modifier: Modifier = Modifier) {

    val hasDog = remember(dog.hasDog) { dog.hasDog }

    Card(
        modifier = modifier
            .padding(2.dp)
            .aspectRatio(1f),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item)),
        onClick = actionClick
    ) {
        Box(
            modifier = modifier.padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (hasDog) {
                AsyncImageFade(
                    data = dog.imgUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(
                        R.string.description_has_dog,
                        dog.index,
                        dog.name
                    )
                )
            } else {
                Text(
                    text = stringResource(id = R.string.index_dog, dog.index),
                    style = MaterialTheme.typography.body1,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}
