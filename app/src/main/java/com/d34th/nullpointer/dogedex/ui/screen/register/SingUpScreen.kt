package com.d34th.nullpointer.dogedex.ui.screen.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.merge

@Destination
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    signUpState: SimpleScreenState = rememberSimpleScreenState()
) {

    LaunchedEffect(key1 = Unit) {
        merge(
            signUpViewModel.messageSignUp,
            authViewModel.messageAuth
        ).collect(signUpState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = signUpState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = stringResource(R.string.title_sign_up),
                actionBack = navigator::popBackStack
            )
        },
        floatingActionButton = {
            if (authViewModel.isAuthenticating) {
                CircularProgressIndicator()
            } else {
                ExtendedFloatingActionButton(
                    text = { Text(stringResource(R.string.text_button_next_sign_up)) },
                    onClick = {
                        signUpViewModel.getDataValid()?.let {
                            authViewModel.signUp(it)
                        }
                    })
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
        ) {
            EditableTextSavable(
                isEnabled = !authViewModel.isAuthenticating,
                valueProperty = signUpViewModel.emailUser,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.size(20.dp))
            EditableTextSavable(
                isEnabled = !authViewModel.isAuthenticating,
                valueProperty = signUpViewModel.passwordUser,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.size(20.dp))
            EditableTextSavable(
                isEnabled = !authViewModel.isAuthenticating,
                valueProperty = signUpViewModel.passwordRepeatUser,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
        }
    }
}