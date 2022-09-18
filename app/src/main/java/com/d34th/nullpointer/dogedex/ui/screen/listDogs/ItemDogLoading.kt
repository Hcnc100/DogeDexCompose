package com.d34th.nullpointer.dogedex.ui.screen.listDogs

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.utils.myShimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer


@Composable
fun DogsLoadings(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    numberLoadingItems: Int = context.resources.getInteger(R.integer.number_dog_loading)
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.size_item_card_dog))
    ) {
        items(numberLoadingItems, key = { it }) {
            ItemDogLoading(modifier = Modifier.myShimmer(shimmer))
        }
    }
}

@Composable
private fun ItemDogLoading(
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = Modifier.padding(2.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item)))
                    .align(Alignment.Center)
                    .then(modifier)
            )
        }
    }


}