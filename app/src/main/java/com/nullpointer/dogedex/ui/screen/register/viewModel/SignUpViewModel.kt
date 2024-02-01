package com.nullpointer.dogedex.ui.screen.register.viewModel

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.core.delegate.PropertySavableString
import com.nullpointer.dogedex.core.delegate.SavableComposeState
import com.nullpointer.dogedex.core.utils.ExceptionManager
import com.nullpointer.dogedex.core.utils.launchSafeIO
import com.nullpointer.dogedex.domain.auth.AuthRepository
import com.nullpointer.dogedex.models.auth.dto.SignUpDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : ViewModel() {

    var isAuthenticating by SavableComposeState(savedStateHandle, KEY_IS_AUTH, false)
        private set

    companion object {
        private const val MAX_LENGTH_EMAIL = 40
        private const val MAX_LENGTH_PASS = 40
        private const val TAG_EMAIL_USER = "TAG_SIGN_UP_EMAIL_USER"
        private const val TAG_PASS_USER = "TAG_SIGN_UP_PASS_USER"
        private const val TAG_PASS_REPEAT_USER = "TAG_SIGN_UP_PASS_REPEAT_USER"
        private const val KEY_IS_AUTH = "KEY_IS_AUTH"
    }

    private val _messageSignUp = Channel<Int>()
    val messageSignUp = _messageSignUp.receiveAsFlow()

    val emailUser = PropertySavableString(
        savedState = savedStateHandle,
        tagSavable = TAG_EMAIL_USER,
        maxLength = MAX_LENGTH_EMAIL,
        hint = R.string.hint_email,
        label = R.string.label_email,
        emptyError = R.string.error_empty_email,
        lengthError = R.string.error_length_email
    )

    val passwordUser = PropertySavableString(
        savedState = savedStateHandle,
        tagSavable = TAG_PASS_USER,
        maxLength = MAX_LENGTH_PASS,
        hint = R.string.hint_password,
        label = R.string.label_password,
        emptyError = R.string.error_empty_password,
        lengthError = R.string.error_length_password
    )

    val passwordRepeatUser = PropertySavableString(
        savedState = savedStateHandle,
        tagSavable = TAG_PASS_REPEAT_USER,
        maxLength = MAX_LENGTH_PASS,
        label = R.string.label_password,
        hint = R.string.hint_repeat_password,
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
            !Patterns.EMAIL_ADDRESS.matcher(emailUser.currentValue).matches() -> {
                emailUser.setAnotherError(R.string.error_valid_email)
                _messageSignUp.trySend(R.string.error_data_invalid)
                null
            }
            passwordUser.currentValue != passwordRepeatUser.currentValue -> {
                passwordRepeatUser.setAnotherError(R.string.error_pass_repeat)
                _messageSignUp.trySend(R.string.error_data_invalid)
                null
            }
            else -> SignUpDTO(
                emailUser.currentValue,
                passwordUser.currentValue,
                passwordRepeatUser.currentValue
            )
        }
    }


    fun signUp(userFieldSignUp: SignUpDTO) = launchSafeIO(
        blockBefore = { isAuthenticating = true },
        blockAfter = { isAuthenticating = false },
        blockIO = { authRepository.signUp(userFieldSignUp) },
        blockException = { _messageSignUp.trySend(
            ExceptionManager.showMessageForException(
                it,
                "signUp"
            )
        ) }
    )

}