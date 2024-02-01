package com.nullpointer.dogedex.ui.screen.listDogs.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.ui.preview.config.OrientationPreviews


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDogsSuccess(
    listDogData: List<DogData>,
    modifier: Modifier = Modifier,
    clickDetails: (DogData) -> Unit,
    isRefreshing: Boolean,
    pullRefreshState: PullRefreshState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyVerticalGrid(
            modifier = modifier,
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            columns = GridCells.Adaptive(dimensionResource(id = R.dimen.size_item_card_dog))
        ) {
            items(
                listDogData,
                key = { it.id },
            ) { dog ->
                ItemDog(
                    dogData = dog,
                    actionClick = { clickDetails(dog) },
                    modifier = Modifier.animateItemPlacement(),
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@OrientationPreviews
@Composable
private fun ListDogsSuccessPreview() {
    ListDogsSuccess(
        listDogData = DogData.listDogs,
        clickDetails = {},
        isRefreshing = false,
        pullRefreshState = rememberPullRefreshState(
            refreshing = true,
            onRefresh = { /*TODO*/ }
        )
    )
}