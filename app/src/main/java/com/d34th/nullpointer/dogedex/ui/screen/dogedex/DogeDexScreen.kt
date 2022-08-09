package com.d34th.nullpointer.dogedex.ui.screen.dogedex

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DogDetailsDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun DogeDexScreen(
    dogsViewModel: DogsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val stateListDogs by dogsViewModel.stateListDogs.collectAsState()
    Scaffold { paddingValues ->
        DogeDexScreen(
            stateListDogs = stateListDogs,
            modifier = Modifier.padding(paddingValues),
            clickDetails = {
                navigator.navigate(DogDetailsDestination(it))
            }
        )
    }
}

@Composable
private fun DogeDexScreen(
    stateListDogs: Resource<List<Dog>>,
    clickDetails: (Dog) -> Unit,
    modifier: Modifier = Modifier
) {
    when (stateListDogs) {

        is Resource.Success -> ListDogsSuccess(
            listDog = stateListDogs.data,
            modifier = modifier,
            clickDetails = clickDetails)
        else -> DogsLoadings(modifier = modifier)
    }
}
