package com.d34th.nullpointer.dogedex.ui.share


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.d34th.nullpointer.dogedex.R

@Composable
fun ToolbarBack(title: String, actionBack: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = { Text(title) },
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = actionBack) {
                Icon(
                    painterResource(id = R.drawable.ic_back),
                    stringResource(id = R.string.description_arrow_back)
                )
            }
        })
}
