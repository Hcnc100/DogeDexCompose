package com.d34th.nullpointer.dogedex.ui.screen.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d34th.nullpointer.dogedex.ui.share.EditableTextSavable
import com.d34th.nullpointer.dogedex.ui.share.ToolbarBack
import com.d34th.nullpointer.dogedex.ui.states.SimpleScreenState
import com.d34th.nullpointer.dogedex.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    signUpState: SimpleScreenState = rememberSimpleScreenState()
) {

    LaunchedEffect(key1 = Unit) {
        signUpViewModel.messageSignUp.collect(signUpState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = signUpState.scaffoldState,
        topBar = { ToolbarBack(title = "Registro", actionBack = navigator::popBackStack) },
        floatingActionButton = {
            ExtendedFloatingActionButton(text = { Text("Continuar") }, onClick = {
                val dataUser = signUpViewModel.getDataValid()
                Timber.d("$dataUser")
            })
        }, floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
        ) {
            EditableTextSavable(
                valueProperty = signUpViewModel.emailUser,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.size(20.dp))
            EditableTextSavable(
                valueProperty = signUpViewModel.passwordUser,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.size(20.dp))
            EditableTextSavable(
                valueProperty = signUpViewModel.passwordRepeatUser,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
        }
    }
}