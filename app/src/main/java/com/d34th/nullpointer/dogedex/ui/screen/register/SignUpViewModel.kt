package com.d34th.nullpointer.dogedex.ui.screen.register

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.delegate.PropertySavableString
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val MAX_LENGTH_EMAIL = 40
        private const val MAX_LENGTH_PASS = 40
    }

    private val _messageSignUp = Channel<Int>()
    val messageSignUp = _messageSignUp.receiveAsFlow()

    val emailUser = PropertySavableString(
        savedStateHandle,
        label = R.string.label_email,
        hint = R.string.hint_email,
        maxLength = MAX_LENGTH_EMAIL,
        emptyError = R.string.error_empty_email,
        lengthError = R.string.error_length_email
    )

    val passwordUser = PropertySavableString(
        savedStateHandle,
        label = R.string.label_password,
        hint = R.string.hint_password,
        maxLength = MAX_LENGTH_PASS,
        emptyError = R.string.error_empty_password,
        lengthError = R.string.error_length_password
    )

    val passwordRepeatUser = PropertySavableString(
        savedStateHandle,
        label = R.string.label_password,
        hint = R.string.hint_repeat_password,
        maxLength = MAX_LENGTH_PASS,
        emptyError = R.string.error_empty_password,
        lengthError = R.string.error_length_password
    )

    private val hasError get() = emailUser.hasError || passwordUser.hasError || passwordRepeatUser.hasError

    fun getDataValid(): SignUpDTO? {
        emailUser.reValueField()
        passwordUser.reValueField()
        passwordRepeatUser.reValueField()
        return when {
            hasError -> {
                _messageSignUp.trySend(R.string.error_data_invalid)
                null
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailUser.value).matches() -> {
                emailUser.setAnotherError(R.string.error_valid_email)
                null
            }
            passwordUser.value != passwordRepeatUser.value -> {
                passwordRepeatUser.setAnotherError(R.string.error_pass_repeat)
                null
            }
            else -> SignUpDTO(emailUser.value, passwordUser.value, passwordRepeatUser.value)
        }
    }
}