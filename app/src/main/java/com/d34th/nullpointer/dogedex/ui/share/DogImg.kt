package com.d34th.nullpointer.dogedex.ui.share

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.d34th.nullpointer.dogedex.R

@Composable
fun DogImg(
    urlImg: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val painter =
        rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(context)
                .data(urlImg)
                .build(),
            error = painterResource(id = R.drawable.ic_broken),
            placeholder = painterResource(id = R.drawable.ic_image)
        )
    AsyncImage(
        model = painter,
        modifier = modifier,
        contentDescription = stringResource(id = R.string.description_img_dog)
    )
}