package com.d34th.nullpointer.dogedex.ui.screen.dogedex

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.Dog

@Composable
fun DogeDexScreen() {
    Scaffold {
        DogeDexScreen(
            stateListDogs = Resource.Loading,
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
        Resource.Failure -> TODO()
        Resource.Loading -> TODO()
        is Resource.Success -> TODO()
    }
}