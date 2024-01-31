package com.d34th.nullpointer.dogedex.ui.screen.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.preview.config.SimplePreview
import com.d34th.nullpointer.dogedex.ui.screen.details.actions.DogDetailsActions
import com.d34th.nullpointer.dogedex.ui.screen.details.componets.ButtonSaveDog
import com.d34th.nullpointer.dogedex.ui.screen.details.componets.CardDogInfo
import com.d34th.nullpointer.dogedex.ui.screen.details.componets.ImageDog
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun DogDetails(
    dogData: DogData,
    navigator: DestinationsNavigator,
    dogsViewModel: DogsViewModel = hiltViewModel(),
    dogDetailsState: SimpleScreenState = rememberSimpleScreenState()
) {
    var hasDog by remember {
        mutableStateOf(dogData.hasDog)
    }

    val title = remember(hasDog) {
        when (hasDog) {
            false -> R.string.title_details_saved_dog
            true -> R.string.title_details_new_dog
        }
    }

    LaunchedEffect(key1 = Unit) {
        dogsViewModel.messageDogs.collect(dogDetailsState::showSnackMessage)
    }

    DogDetails(
        dogData = dogData,
        scaffoldState = dogDetailsState.scaffoldState,
        titleString = title,
        onDogDetailsActions = { action ->
            when (action) {
                DogDetailsActions.BACK -> navigator.popBackStack()
                DogDetailsActions.SAVE_DOG -> dogsViewModel.addDog(
                    dogData = dogData,
                    callbackSuccess = { hasDog = true }
                )
            }
        }
    )
}

@Composable
fun DogDetails(
    dogData: DogData,
    @StringRes
    titleString: Int,
    scaffoldState: ScaffoldState,
    onDogDetailsActions: (DogDetailsActions) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ButtonSaveDog(
                isVisible = !dogData.hasDog,
                actionBack = { onDogDetailsActions(DogDetailsActions.SAVE_DOG) }
            )
        },
        topBar = {
            ToolbarBack(
                title = stringResource(id = titleString),
                actionBack = { onDogDetailsActions(DogDetailsActions.BACK) },
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ImageDog(dogData = dogData)
                CardDogInfo(dogData = dogData)
            }
        }
    }
}


@SimplePreview
@Composable
fun DogDetailsPreview() {
    DogDetails(
        onDogDetailsActions = {},
        dogData = DogData.exampleHasDog,
        scaffoldState = rememberScaffoldState(),
        titleString = R.string.title_details_new_dog
    )
}

