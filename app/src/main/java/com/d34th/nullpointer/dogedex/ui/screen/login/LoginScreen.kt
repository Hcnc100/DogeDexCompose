package com.d34th.nullpointer.dogedex.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SignUpScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.login.test.LoginTestTags
import com.d34th.nullpointer.dogedex.ui.screen.login.viewModel.LoginViewModel
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.d34th.nullpointer.dogedex.ui.share.PasswordTextSavable
import com.d34th.nullpointer.dogedex.ui.states.FieldsScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberFieldsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.merge

@Destination(start = true)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel = hiltViewModel(),
    loginScreenState: FieldsScreenState = rememberFieldsScreenState()
) {

    LaunchedEffect(key1 = Unit) {
        merge(
            loginViewModel.messageLogin,
            authViewModel.messageAuth
        ).collect(loginScreenState::showSnackMessage)
    }
    Scaffold(
        scaffoldState = loginScreenState.scaffoldState,
        backgroundColor = MaterialTheme.colors.primary

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoApp(modifier = Modifier.weight(.33f))
            Column(
                modifier = Modifier
                    .weight(.66f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ContainerLogin(
                    modifier = Modifier.width(300.dp),
                    emailValue = loginViewModel.emailLogin,
                    passwordValue = loginViewModel.passwordLogin,
                    isEnableFields = authViewModel.isAuthenticating,
                    moveNextAction = loginScreenState::downAnotherField,
                    signInAction = {
                        loginScreenState.clearFocus()
                        loginViewModel.getCredentialAndValidate()?.let {
                            authViewModel.signIn(it)
                        }
                    }
                )

                ButtonsSignInAndSignUp(
                    isAuthenticating = authViewModel.isAuthenticating,
                    actionSignUp = { navigator.navigate(SignUpScreenDestination) },
                    actionSignIn = {
                        loginViewModel.getCredentialAndValidate()?.let {
                            authViewModel.signIn(it)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun LogoApp(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Card(modifier = Modifier.size(150.dp), shape = CircleShape) {
            AsyncImage(
                modifier = Modifier.padding(20.dp),
                model = R.drawable.ic_dog,
                contentDescription = stringResource(id = R.string.app_name)
            )
        }
    }

}

@Composable
private fun ContainerLogin(
    isEnableFields: Boolean,
    signInAction: () -> Unit,
    moveNextAction: () -> Unit,
    emailValue: PropertySavableString,
    passwordValue: PropertySavableString,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
       ContainerFieldAuth {
           EditableTextSavable(
               singleLine = true,
               isEnabled = !isEnableFields,
               valueProperty = emailValue,
               modifier = Modifier.padding(10.dp),
               modifierText = Modifier.semantics { testTag = LoginTestTags.INPUT_EMAIL },
               keyboardOptions = KeyboardOptions.Default.copy(
                   keyboardType = KeyboardType.Email,
                   imeAction = ImeAction.Next,
                   autoCorrect = false
               ),
               keyboardActions = KeyboardActions(
                   onNext = { moveNextAction() }
               )
           )
       }

        Spacer(modifier = Modifier.size(20.dp))

        ContainerFieldAuth {
            PasswordTextSavable(
                singleLine = true,
                isEnabled = !isEnableFields,
                valueProperty = passwordValue,
                modifier = Modifier.padding(10.dp),
                modifierText = Modifier.semantics { testTag = LoginTestTags.INPUT_PASSWORD },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { signInAction() }
                )
            )
        }
    }
}


@Composable
private fun ButtonsSignInAndSignUp(
    isAuthenticating: Boolean,
    actionSignUp: () -> Unit,
    actionSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isAuthenticating) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.semantics { testTag = LoginTestTags.INDICATOR_PROGRESS }
            )
        }
    } else {
        Column(modifier) {
            ExtendedFloatingActionButton(
                modifier = Modifier.fillMaxWidth(),
                text = { Text(stringResource(R.string.text_button_sign_in)) },
                onClick = actionSignIn
            )
            Spacer(modifier = Modifier.size(10.dp))
            CreateAccount(
                actionClick = actionSignUp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }


}

@Composable
private fun CreateAccount(
    actionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(R.string.text_has_not_account))
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedButton(onClick = actionClick) {
            Text(text = stringResource(R.string.text_button_go_sign_up))
        }
    }
}

@Composable
private fun ContainerFieldAuth(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.background)
    ) {
        content()
    }
}
