package com.d34th.nullpointer.dogedex.ui.screen.login.viewModel

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val EMAIL_LENGTH = 40
        private const val PASSWORD_LENGTH = 30
    }

    private val _messageLogin = Channel<String>()
    val messageLogin = _messageLogin.receiveAsFlow()

    val emailLogin = PropertySavableString(
        state = savedStateHandle,
        label = R.string.label_email,
        hint = R.string.hint_email,
        maxLength = EMAIL_LENGTH,
        emptyError = R.string.error_empty_email,
        lengthError = R.string.error_length_email
    )

    val passwordLogin = PropertySavableString(
        state = savedStateHandle,
        label = R.string.label_password,
        hint = R.string.hint_password,
        maxLength = PASSWORD_LENGTH,
        emptyError = R.string.error_empty_password,
        lengthError = R.string.error_length_password
    )


    fun getCredentialAndValidate(): UserFieldSignIn? {
        emailLogin.reValueField()
        passwordLogin.reValueField()
        return when {
            emailLogin.hasError || passwordLogin.hasError -> {
                _messageLogin.trySend("Verifique sus datos")
                null
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailLogin.value).matches() -> {
                emailLogin.setAnotherError(R.string.error_valid_email)
                null
            }
            else -> UserFieldSignIn(emailLogin.value, passwordLogin.value)
        }
    }


}