package com.d34th.nullpointer.dogedex.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.states.AuthState
import com.d34th.nullpointer.dogedex.models.User
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
                    user = currentUser.currentUser
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
    user: User
) {
    Column(modifier = modifier.padding(10.dp)) {
        Text(text = stringResource(R.string.title_info_user), style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = stringResource(R.string.title_id_user, user.id))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = stringResource(R.string.title_email_user, user.email))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = stringResource(R.string.title_token_user, user.token))
    }
}