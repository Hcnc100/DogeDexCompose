package com.nullpointer.dogedex.ui.screen.camera

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nullpointer.dogedex.R

@Composable
fun PermissionsCameraScreen(
    modifier: Modifier = Modifier,
    isFirstRequest: Boolean,
    launchPermission: () -> Unit,
    launchOpenSettings: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieContainer(
            animation = R.raw.cute_dog,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            stringResource(R.string.message_permission_camera_is_necesary),
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (isFirstRequest) {
                    launchPermission()
                } else {
                    launchOpenSettings()
                }
            }
        ) {
            Text(text = stringResource(R.string.message_button_lauch_camera_permission))
        }
    }
}

@Composable
private fun LottieContainer(modifier: Modifier, @RawRes animation: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier,
    )
}
