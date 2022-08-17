package com.d34th.nullpointer.dogedex.ui.screen.camera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.d34th.nullpointer.dogedex.R

@Composable
fun MessageCamera(
    modifier: Modifier = Modifier,
    isFirstRequest: Boolean,
    changeFirstRequest: (Boolean) -> Unit,
    launchPermission: () -> Unit,
    context: Context = LocalContext.current
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieContainer(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            animation = R.raw.cute_dog
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text("Se requiere el permiso de la camara", style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (isFirstRequest) {
                    launchPermission()
                    changeFirstRequest(false)
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + context.packageName)
                    context.startActivity(intent)
                }
            }
        ) {
            Text(text = "Conceder permiso")
        }
    }
}

@Composable
fun LottieContainer(modifier: Modifier, @RawRes animation: Int) {
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
