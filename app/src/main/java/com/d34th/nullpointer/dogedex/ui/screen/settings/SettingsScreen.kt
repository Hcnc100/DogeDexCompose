package com.d34th.nullpointer.dogedex.ui.screen.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = { ToolbarBack(title = "Settings", actionBack = navigator::popBackStack) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.width(300.dp),
                text = { Text(text = "LogOut") },
                onClick = authViewModel::signOut
            )
        }
    ) {
        Box(modifier = Modifier.padding(it))
    }
}