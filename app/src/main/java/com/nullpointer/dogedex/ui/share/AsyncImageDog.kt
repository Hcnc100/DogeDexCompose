package com.nullpointer.dogedex.ui.share

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.core.utils.getGrayColor
import com.nullpointer.dogedex.core.utils.isSuccess

@Composable
fun AsyncImageFade(
    data: Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    @DrawableRes
    resourceLoading: Int = R.drawable.ic_image,
    @DrawableRes
    resourceFailure: Int = R.drawable.ic_broken,
    context: Context = LocalContext.current
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(context)
            .data(data)
            .build(),
        error = painterResource(id = resourceFailure),
        placeholder = painterResource(id = resourceLoading)
    )
    Image(
        painter = painter,
        modifier = modifier,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        colorFilter = if (painter.isSuccess) null else ColorFilter.tint(getGrayColor())
    )
}