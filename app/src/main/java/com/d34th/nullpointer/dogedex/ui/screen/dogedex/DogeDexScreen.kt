package com.d34th.nullpointer.dogedex.ui.screen.dogedex

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DirectionDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SettingsScreenDestination
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DogeDexScreen(
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
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { MainButtons(changeDestination = navigator::navigate) }
    ) { paddingValues ->
        DogeDexScreen(
            stateListDogs = stateListDogs,
            modifier = Modifier.padding(paddingValues),
            clickDetails = {
//                navigator.navigate(DogDetailsDestination(it))
                dogsViewModel.addDog(it)
            }
        )
    }
}

@Composable
private fun MainButtons(
    modifier: Modifier = Modifier,
    changeDestination: (DirectionDestination) -> Unit
) {
    Row(modifier = modifier) {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "")
        }
        Spacer(modifier = Modifier.width(20.dp))
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "")
        }
        Spacer(modifier = Modifier.width(20.dp))
        FloatingActionButton(onClick = { changeDestination(SettingsScreenDestination) }) {
            Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        }
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
