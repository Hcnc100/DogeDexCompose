package com.d34th.nullpointer.dogedex.ui.screen.listDogs

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun DogsLoadings(
    modifier: Modifier = Modifier
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.size_item_card_dog))
    ) {
        items(20, key = { it }) {
            ItemDogLoading(shimmer = shimmer)
        }
    }
}

@Composable
private fun ItemDogLoading(
    shimmer: Shimmer,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {

    val backgroundColor by remember(isDark) {
        derivedStateOf { if (isDark) Color.DarkGray else Color.LightGray }
    }

    Card(
        modifier = modifier.padding(2.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_card_dog_item))
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.size_img_dog_item))
                    .clip(RoundedCornerShape(10.dp))
                    .shimmer(shimmer)
                    .background(backgroundColor)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(20.dp)
                    .shimmer(shimmer)
                    .background(backgroundColor)
            )
        }
    }


}