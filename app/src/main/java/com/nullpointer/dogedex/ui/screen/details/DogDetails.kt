package com.nullpointer.dogedex.ui.screen.details

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.presentation.DogsViewModel
import com.nullpointer.dogedex.ui.preview.config.OrientationPreviews
import com.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.nullpointer.dogedex.ui.screen.details.actions.DogDetailsActions
import com.nullpointer.dogedex.ui.screen.details.componets.ButtonSaveDog
import com.nullpointer.dogedex.ui.screen.details.componets.CardDogInfo
import com.nullpointer.dogedex.ui.screen.details.componets.ImageDog
import com.nullpointer.dogedex.ui.share.ToolbarBack
import com.nullpointer.dogedex.ui.states.SimpleScreenState
import com.nullpointer.dogedex.ui.states.rememberSimpleScreenState
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


    LaunchedEffect(key1 = Unit) {
        dogsViewModel.messageDogs.collect(dogDetailsState::showSnackMessage)
    }

    DogDetails(
        hasDog = hasDog,
        dogData = dogData,
        scaffoldState = dogDetailsState.scaffoldState,
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
    hasDog: Boolean,
    scaffoldState: ScaffoldState,
    onDogDetailsActions: (DogDetailsActions) -> Unit,
    orientation: Int = LocalConfiguration.current.orientation
) {

    val title = remember(hasDog) {
        when (hasDog) {
            false -> R.string.title_details_new_dog
            true -> R.string.title_details_saved_dog
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ToolbarBack(
                title = stringResource(id = title),
                actionBack = { onDogDetailsActions(DogDetailsActions.BACK) },
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {

            when (orientation) {
                ORIENTATION_PORTRAIT -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ImageDog(dogData = dogData)
                        CardDogInfo(dogData = dogData)
                        Spacer(modifier = Modifier.padding(16.dp))
                        ButtonSaveDog(
                            isVisible = !hasDog,
                            actionBack = { onDogDetailsActions(DogDetailsActions.SAVE_DOG) }
                        )
                    }
                }

                else -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(50.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ImageDog(dogData = dogData)
                            ButtonSaveDog(
                                isVisible = !hasDog,
                                actionBack = { onDogDetailsActions(DogDetailsActions.SAVE_DOG) }
                            )
                        }
                        CardDogInfo(dogData = dogData)
                    }
                }
            }
        }
    }
}


@OrientationPreviews
@Composable
fun DogDetailsPreview(
    @PreviewParameter(BooleanProvider::class)
    hasDog: Boolean
) {
    DogDetails(
        hasDog = hasDog,
        onDogDetailsActions = {},
        dogData = DogData.exampleHasDog,
        scaffoldState = rememberScaffoldState()
    )
}

