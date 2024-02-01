package com.nullpointer.dogedex.ui.screen.listDogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.dogedex.core.states.Resource
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.navigation.HomeNavGraph
import com.nullpointer.dogedex.presentation.DogsViewModel
import com.nullpointer.dogedex.ui.interfaces.ActionRootDestinations
import com.nullpointer.dogedex.ui.screen.destinations.DogDetailsDestination
import com.nullpointer.dogedex.ui.screen.listDogs.components.ListDogsSuccess
import com.nullpointer.dogedex.ui.screen.listDogs.test.ListDogsTestTag
import com.nullpointer.dogedex.ui.share.BlockProcessing
import com.nullpointer.dogedex.ui.states.DogsScreenState
import com.nullpointer.dogedex.ui.states.rememberDogsScreenState
import com.ramcosta.composedestinations.annotation.Destination

@HomeNavGraph
@Destination
@Composable
fun ListDogsScreen(
    actionRootDestinations: ActionRootDestinations,
    dogsViewModel: DogsViewModel = hiltViewModel(),
    dogeDexState: DogsScreenState = rememberDogsScreenState(
        isRefreshing = false,
        onRefresh = dogsViewModel::requestMyLastDogs,
    )
) {
    val stateListDogs by dogsViewModel.stateListDogs.collectAsState()

    LaunchedEffect(key1 = Unit) {
        dogsViewModel.messageDogs.collect(dogeDexState::showSnackMessage)
    }

    ListDogsScreen(
        stateListDogs = stateListDogs,
        scaffoldState = dogeDexState.scaffoldState,
        pullRefreshState = dogeDexState.pullRefreshState,
        isRefreshing = dogsViewModel.isLoadingMyGogs,
        clickDetails = { dogData ->
            actionRootDestinations.changeRoot(DogDetailsDestination(dogData))
        },
    )
}

@Composable
private fun ListDogsScreen(
    isRefreshing: Boolean,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    clickDetails: (DogData) -> Unit,
    pullRefreshState: PullRefreshState,
    stateListDogs: Resource<List<DogData>>,
) {

    Scaffold(
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .then(
                    when (stateListDogs) {
                        is Resource.Success -> Modifier.pullRefresh(pullRefreshState)
                        else -> Modifier
                    }
                )
        ) {
            when (stateListDogs) {
                is Resource.Success -> {
                    ListDogsSuccess(
                        isRefreshing = isRefreshing,
                        clickDetails = clickDetails,
                        listDogData = stateListDogs.data,
                        pullRefreshState = pullRefreshState,
                        modifier = modifier.semantics { testTag = ListDogsTestTag.LIST_DOGS }
                    )
                }

                else -> BlockProcessing()
            }
        }
    }
}
