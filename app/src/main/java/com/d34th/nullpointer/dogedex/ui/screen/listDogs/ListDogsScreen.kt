package com.d34th.nullpointer.dogedex.ui.screen.listDogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DogDetailsDestination
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ListDogsScreen(
    navigator: DestinationsNavigator,
    dogsViewModel: DogsViewModel = hiltViewModel(),
    dogeDexState: SimpleScreenState = rememberSimpleScreenState(),
) {
    val stateListDogs by dogsViewModel.stateListDogs.collectAsState()
    LaunchedEffect(key1 = Unit) {
        dogsViewModel.messageDogs.collect(dogeDexState::showSnackMessage)
    }
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

@Composable
private fun ListDogsScreen(
    stateListDogs: Resource<List<Dog>>,
    clickDetails: (Dog) -> Unit,
    modifier: Modifier = Modifier
) {
    when (stateListDogs) {

        is Resource.Success -> ListDogsSuccess(
            listDog = stateListDogs.data,
            modifier = modifier,
            clickDetails = clickDetails
        )
        else -> DogsLoadings(modifier = modifier)
    }
}
