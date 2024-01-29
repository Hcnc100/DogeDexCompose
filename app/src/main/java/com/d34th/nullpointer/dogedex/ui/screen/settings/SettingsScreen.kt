package com.d34th.nullpointer.dogedex.ui.screen.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.states.AuthState
import com.d34th.nullpointer.dogedex.models.auth.data.AuthData
import com.d34th.nullpointer.dogedex.navigation.HomeNavGraph
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph
@Destination
@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val stateUser by authViewModel.stateUser.collectAsState()

    Scaffold(
        topBar = {
            ToolbarBack(
                title = stringResource(R.string.title_settings),
                actionBack = navigator::popBackStack
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.width(300.dp),
                text = { Text(text = stringResource(R.string.text_button_sign_out)) },
                onClick = authViewModel::signOut
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        when (val currentUser = stateUser) {
            is AuthState.Authenticated -> {
                UserAuthInfo(
                    modifier = Modifier.padding(it),
                    authData = currentUser.currentUser
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun UserAuthInfo(
    modifier: Modifier = Modifier,
    authData: AuthData
) {
    Column(modifier = modifier.padding(10.dp)) {
        Text(text = stringResource(R.string.title_info_user), style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = stringResource(R.string.title_id_user, authData.id))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = stringResource(R.string.title_email_user, authData.email))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = stringResource(R.string.title_token_user, authData.token))
    }
}