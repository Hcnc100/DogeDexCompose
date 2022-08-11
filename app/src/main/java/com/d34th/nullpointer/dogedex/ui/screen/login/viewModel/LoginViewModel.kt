package com.d34th.nullpointer.dogedex.ui.screen.login.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val EMAIL_LENGTH = 40
        private const val PASSWORD_LENGTH = 30
    }

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

    val isValid get() = emailLogin.hasError && emailLogin.value.isNotEmpty()
            || passwordLogin.hasError && passwordLogin.value.isNotEmpty()


}