package com.nullpointer.dogedex.ui.screen.login.viewModel

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.core.delegate.PropertySavableString
import com.nullpointer.dogedex.core.delegate.SavableComposeState
import com.nullpointer.dogedex.core.utils.ExceptionManager
import com.nullpointer.dogedex.core.utils.launchSafeIO
import com.nullpointer.dogedex.domain.auth.AuthRepository
import com.nullpointer.dogedex.models.auth.dto.SignInDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : ViewModel() {

    var isAuthenticating by SavableComposeState(savedStateHandle, KEY_IS_AUTH, false)
        private set

    private val _messageAuth = Channel<Int>()
    val messageAuth = _messageAuth.receiveAsFlow()

    companion object {
        private const val EMAIL_LENGTH = 40
        private const val PASSWORD_LENGTH = 30
        private const val KEY_IS_AUTH = "KEY_IS_AUTH"
        private const val TAG_EMAIL_USER = "TAG_SIGN_IN_EMAIL_USER"
        private const val TAG_PASSWORD_USER = "TAG_SIGN_IN_PASSWORD_USER"
    }

    private val _messageLogin = Channel<Int>()
    val messageLogin = _messageLogin.receiveAsFlow()

    val emailLogin = PropertySavableString(
        maxLength = EMAIL_LENGTH,
        tagSavable = TAG_EMAIL_USER,
        savedState = savedStateHandle,
        hint = R.string.hint_email,
        label = R.string.label_email,
        emptyError = R.string.error_empty_email,
        lengthError = R.string.error_length_email
    )

    val passwordLogin = PropertySavableString(
        savedState = savedStateHandle,
        label = R.string.label_password,
        hint = R.string.hint_password,
        maxLength = PASSWORD_LENGTH,
        emptyError = R.string.error_empty_password,
        lengthError = R.string.error_length_password,
        tagSavable = TAG_PASSWORD_USER
    )


    fun getCredentialAndValidate(): SignInDTO? {
        emailLogin.reValueField()
        passwordLogin.reValueField()
        return when {
            emailLogin.hasError || passwordLogin.hasError -> {
                _messageLogin.trySend(R.string.error_data_invalid)
                null
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailLogin.currentValue).matches() -> {
                emailLogin.setAnotherError(R.string.error_valid_email)
                _messageLogin.trySend(R.string.error_data_invalid)
                null
            }
            else -> SignInDTO(emailLogin.currentValue, passwordLogin.currentValue)
        }
    }

    fun signIn(
        userFieldsSignIn: SignInDTO
    ) = launchSafeIO(
        blockBefore = { isAuthenticating = true },
        blockAfter = { isAuthenticating = false },
        blockIO = { authRepository.signIn(userFieldsSignIn) },
        blockException = { _messageLogin.trySend(
            ExceptionManager.showMessageForException(
                it,
                "signIn"
            )
        ) }
    )




}