package com.d34th.nullpointer.dogedex.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SignUpScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.login.viewModel.LoginViewModel
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Scaffold(backgroundColor = MaterialTheme.colors.primary) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoApp()
            ContainerLogin(
                modifier = Modifier.width(300.dp),
                emailValue = loginViewModel.emailLogin,
                passwordValue = loginViewModel.passwordLogin,
                actionClick = {
                    navigator.navigate(SignUpScreenDestination)
                }
            )
        }
    }
}

@Composable
private fun LogoApp(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.size(150.dp), shape = CircleShape) {}
}

@Composable
private fun ContainerLogin(
    modifier: Modifier = Modifier,
    emailValue: PropertySavableString,
    passwordValue: PropertySavableString,
    actionClick: () -> Unit,
) {
    Column(modifier = modifier) {


       ContainerFieldAuth {
           EditableTextSavable(
               valueProperty = emailValue,
               modifier = Modifier.padding(10.dp),
               keyboardOptions = KeyboardOptions.Default.copy(
                   keyboardType = KeyboardType.Email,
                   capitalization = KeyboardCapitalization.None
               )
           )
       }

        Spacer(modifier = Modifier.size(20.dp))

        ContainerFieldAuth {
            EditableTextSavable(
                valueProperty = passwordValue,
                modifier = Modifier.padding(10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                )
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        ExtendedFloatingActionButton(
            modifier = Modifier.fillMaxWidth(),
            text = { Text("Registro") },
            onClick = actionClick
        )
        Spacer(modifier = Modifier.size(10.dp))
        CreateAccount(
            actionClick = actionClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun CreateAccount(
    actionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "¿No tienes cuenta?")
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedButton(onClick = actionClick) {
            Text(text = "Registrate")
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
