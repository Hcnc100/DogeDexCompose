package com.d34th.nullpointer.dogedex.ui.screen.listDogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DogDetailsDestination
import com.d34th.nullpointer.dogedex.ui.screen.listDogs.test.ListDogsTestTag
import com.d34th.nullpointer.dogedex.ui.states.DogsScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberDogsScreenState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ListDogsScreen(
    navigator: DestinationsNavigator,
    dogsViewModel: DogsViewModel = hiltViewModel(),
    dogeDexState: DogsScreenState = rememberDogsScreenState(isRefreshing = dogsViewModel.isLoadingMyGogs)
) {
    val stateListDogs by dogsViewModel.stateListDogs.collectAsState()

    LaunchedEffect(key1 = Unit) {
        dogsViewModel.messageDogs.collect(dogeDexState::showSnackMessage)
    }

    SwipeRefresh(
        state = dogeDexState.swipeRefreshState,
        onRefresh = dogsViewModel::requestMyLastDogs,
    ) {
        Scaffold(
            scaffoldState = dogeDexState.scaffoldState,
        ) { paddingValues ->
            ListDogsScreen(
                stateListDogs = stateListDogs,
                modifier = Modifier.padding(paddingValues),
                clickDetails = {
                    navigator.navigate(DogDetailsDestination(it, false))
                }
            )
        }
    }

}

@Composable
private fun ListDogsScreen(
    stateListDogs: Resource<List<Dog>>,
    clickDetails: (Dog) -> Unit,
    modifier: Modifier = Modifier
) {
    when (stateListDogs) {

        is Resource.Success -> ListDogsSuccess(
            listDog = stateListDogs.data,
            modifier = modifier.semantics { testTag = ListDogsTestTag.LIST_DOGS },
            clickDetails = clickDetails
        )
        else -> DogsLoadings(modifier = modifier.semantics {
            testTag = ListDogsTestTag.LOADING_LIST
        })
    }
}
