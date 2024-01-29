package com.d34th.nullpointer.dogedex.ui.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.share.AsyncImageFade
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
    isNewDog: Boolean,
    navigator: DestinationsNavigator,
    dogsViewModel: DogsViewModel = hiltViewModel(),
    dogDetailsState: SimpleScreenState = rememberSimpleScreenState()
) {
    val title = remember {
        if (isNewDog) R.string.title_details_new_dog else R.string.title_details_saved_dog
    }
    LaunchedEffect(key1 = Unit) {
        dogsViewModel.messageDogs.collect(dogDetailsState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = dogDetailsState.scaffoldState,
        floatingActionButton = {
            if (isNewDog)
                ButtonSaveDog(actionBack = {
                    dogsViewModel.addDog(dogData) {
                        navigator.popBackStack()
                    }
                })
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = { ToolbarBack(title = stringResource(id = title), navigator::popBackStack) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            ) {
                ImageDog(dogData = dogData)
                Card(shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HeaderDetailsDogs(dogData = dogData)
                        MoreDetailsDogs(dogData = dogData)
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageDog(
    dogData: DogData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(240.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomEnd),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.index_dog, dogData.id),
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                textAlign = TextAlign.End
            )
        }
        AsyncImageFade(
            data = dogData.imgUrl,
            contentDescription = stringResource(
                R.string.description_has_dog,
                dogData.id,
                dogData.name
            ),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun ButtonSaveDog(
    actionBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        modifier = modifier.padding(horizontal = 20.dp),
        onClick = actionBack,
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.text_save_dog))
                Spacer(modifier = Modifier.size(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = stringResource(R.string.description_button_save_dog)
                )
            }
        })
}


