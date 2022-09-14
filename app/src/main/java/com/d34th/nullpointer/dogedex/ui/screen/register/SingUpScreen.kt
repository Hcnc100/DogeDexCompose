package com.d34th.nullpointer.dogedex.ui.screen.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.screen.register.test.SignUpTestTag
import com.d34th.nullpointer.dogedex.ui.screen.register.viewModel.SignUpViewModel
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.d34th.nullpointer.dogedex.ui.share.PasswordTextSavable
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.d34th.nullpointer.dogedex.ui.states.FieldsScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberFieldsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.merge

@Destination
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    signUpState: FieldsScreenState = rememberFieldsScreenState()
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
            ButtonProgressAuthentication(
                isAuthenticating = authViewModel.isAuthenticating,
                actionClickAuth = {
                    signUpViewModel.getDataValid()?.let {
                        authViewModel.signUp(it)
                    }
                })
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            EditableTextSavable(
                singleLine = true,
                isEnabled = !authViewModel.isAuthenticating,
                valueProperty = signUpViewModel.emailUser,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(
                    onNext = { signUpState.downAnotherField() }
                ),
                modifierText = Modifier.semantics { testTag = SignUpTestTag.INPUT_EMAIL }
            )
            Spacer(modifier = Modifier.size(20.dp))
            PasswordTextSavable(
                singleLine = true,
                isEnabled = !authViewModel.isAuthenticating,
                valueProperty = signUpViewModel.passwordUser,
                modifierText = Modifier.semantics { testTag = SignUpTestTag.INPUT_PASSWORD },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { signUpState.downAnotherField() }
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
            PasswordTextSavable(
                singleLine = true,
                isEnabled = !authViewModel.isAuthenticating,
                valueProperty = signUpViewModel.passwordRepeatUser,
                modifierText = Modifier.semantics {
                    testTag = SignUpTestTag.INPUT_PASSWORD_CONFIRM
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        signUpState.clearFocus()
                        signUpViewModel.getDataValid()?.let {
                            authViewModel.signUp(it)
                        }
                    }
                )
            )
        }
    }
}

@Composable
private fun ButtonProgressAuthentication(
    isAuthenticating: Boolean,
    modifier: Modifier = Modifier,
    actionClickAuth: () -> Unit
) {
    if (isAuthenticating) {
        CircularProgressIndicator(
            modifier = modifier.semantics { testTag = SignUpTestTag.INDICATOR_PROGRESS }
        )
    } else {
        ExtendedFloatingActionButton(
            modifier = modifier,
            text = { Text(stringResource(R.string.text_button_next_sign_up)) },
            onClick = actionClickAuth
        )
    }
}