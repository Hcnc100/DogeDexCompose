package com.d34th.nullpointer.dogedex.ui.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DogDetails(
    dog: Dog,
    isNewDog: Boolean,
    navigator: DestinationsNavigator
) {
    val title by remember {
        derivedStateOf {
            if (isNewDog) "Nuevo perro" else "Info del perro"
        }
    }

    Scaffold(
        floatingActionButton = {
            if (isNewDog) {
                FloatingActionButton(onClick = navigator::popBackStack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = ""
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            ToolbarBack(title = title, navigator::popBackStack)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            ) {
                ImageDog(dog = dog)
                Card(shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HeaderDetailsDogs(dog = dog)
                        MoreDetailsDogs(dog = dog)
                    }
                }

            }
        }
    }
}

@Composable
fun ImageDog(
    dog: Dog, modifier: Modifier = Modifier
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
                text = "#${dog.index}",
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                textAlign = TextAlign.End
            )
        }
        AsyncImage(
            model = dog.imgUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .align(Alignment.BottomCenter)
        )
    }
}


