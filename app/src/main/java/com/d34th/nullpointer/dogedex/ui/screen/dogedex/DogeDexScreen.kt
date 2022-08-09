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

@Composable
fun DogeDexScreen(
    dogsViewModel: DogsViewModel = hiltViewModel()
) {
    val stateListDogs by dogsViewModel.stateListDogs.collectAsState()
    Scaffold {
        DogeDexScreen(
            stateListDogs = stateListDogs,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun DogeDexScreen(
    stateListDogs: Resource<List<Dog>>,
    modifier: Modifier = Modifier
) {
    when (stateListDogs) {
        is Resource.Success -> LazyListDogs(listDog = stateListDogs.data, modifier = modifier)
        else -> DogsLoadings(modifier = modifier)
    }
}
