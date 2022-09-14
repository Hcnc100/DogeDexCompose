package com.d34th.nullpointer.dogedex.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.screen.register.SignUpScreen
import com.d34th.nullpointer.dogedex.ui.screen.register.test.SignUpTestTag
import com.d34th.nullpointer.dogedex.ui.screen.register.viewModel.SignUpViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    private class AuthFakeRepo(
        val delaySignIn: Long = 500
    ) : AuthRepository {
        override val currentUser: Flow<User> = flowOf(User())
        override val isAuthUser: Flow<Boolean> = flowOf(false)

        override suspend fun signIn(userCredentials: SignInDTO) = Unit
        override suspend fun signUp(userCredentials: SignUpDTO) {
            delay(delaySignIn)
        }

        override suspend fun signOut() = Unit

    }

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val navigator: DestinationsNavigatorImpl = DestinationsNavigatorImpl()


    @Test
    fun validateEmptyFields() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthFakeRepo()
        )
        composeTestRule.setContent {
            SignUpScreen(
                authViewModel = authViewModel,
                navigator = navigator,
                signUpViewModel = SignUpViewModel(SavedStateHandle())
            )
        }

        with(composeTestRule) {
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).performClick()
            onAllNodesWithText(context.getString(R.string.error_empty_password)).assertCountEquals(2)
            onNodeWithText(context.getString(R.string.error_empty_email)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.error_data_invalid)).assertIsDisplayed()
        }
    }

    @Test
    fun invalidEmail() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthFakeRepo()
        )
        composeTestRule.setContent {
            SignUpScreen(
                authViewModel = authViewModel,
                navigator = navigator,
                signUpViewModel = SignUpViewModel(SavedStateHandle())
            )
        }

        with(composeTestRule) {
            // * set invalid email
            onNodeWithTag(SignUpTestTag.INPUT_EMAIL).performTextInput("invalid email")

            // * set any password
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD).performTextInput("anypasswor0d")
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD_CONFIRM).performTextInput("anypasswor0d")

            // * launch event
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).performClick()

            onNodeWithText(context.getString(R.string.error_valid_email)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.error_data_invalid)).assertIsDisplayed()
        }
    }


    @Test
    fun passwordNoSame() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthFakeRepo()
        )
        composeTestRule.setContent {
            SignUpScreen(
                authViewModel = authViewModel,
                navigator = navigator,
                signUpViewModel = SignUpViewModel(SavedStateHandle())
            )
        }

        with(composeTestRule) {
            // * set invalid email
            onNodeWithTag(SignUpTestTag.INPUT_EMAIL).performTextInput("generic.email@generic.com")

            // * set any password different
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD).performTextInput("anypasswor0d")
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD_CONFIRM).performTextInput("anypasswor0d124")

            // * launch event
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).performClick()

            onNodeWithText(context.getString(R.string.error_pass_repeat)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.error_data_invalid)).assertIsDisplayed()
        }
    }

    @Test
    fun fieldsLongest() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthFakeRepo()
        )
        composeTestRule.setContent {
            SignUpScreen(
                authViewModel = authViewModel,
                navigator = navigator,
                signUpViewModel = SignUpViewModel(SavedStateHandle())
            )
        }

        with(composeTestRule) {
            // * set long email
            onNodeWithTag(SignUpTestTag.INPUT_EMAIL).performTextInput("generic.easdadssdasdasdaddffasdfasfasfasasmail@generic.com")

            // * set long password
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD).performTextInput("ASDFDSFSADFASFASDFASFDSAFASFAWEFFFQWEQRFQRQRFQFRQ")
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD_CONFIRM).performTextInput("ASDFDSFSADFASFASDFASFDSAFASFAWEFFFQWEQRFQRQRFQFRQ")

            // * launch event
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).performClick()

            // * validate message long fields
            onNodeWithText(context.getString(R.string.error_length_email)).assertIsDisplayed()
            onAllNodesWithText(context.getString(R.string.error_length_password)).assertCountEquals(
                2
            )
        }
    }

    @Test
    fun showProgressIndicatorSignUp() = runTest {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthFakeRepo()
        )
        composeTestRule.setContent {
            SignUpScreen(
                authViewModel = authViewModel,
                navigator = navigator,
                signUpViewModel = SignUpViewModel(SavedStateHandle())
            )
        }

        with(composeTestRule) {
            // * set email
            onNodeWithTag(SignUpTestTag.INPUT_EMAIL).performTextInput("generic.mail@generic.com")

            // * set password
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD).performTextInput("passw0rd1")
            onNodeWithTag(SignUpTestTag.INPUT_PASSWORD_CONFIRM).performTextInput("passw0rd1")

            // * launch event
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).performClick()


            // * when is authenticating the button next no exist
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).assertDoesNotExist()
            waitUntil{ authViewModel.isAuthenticating }

            // * and show progress indicator
            onNodeWithTag(SignUpTestTag.INDICATOR_PROGRESS).assertIsDisplayed()
            waitUntil{ !authViewModel.isAuthenticating }

            // * when finish, so show button next
            onNodeWithText(context.getString(R.string.text_button_next_sign_up)).assertExists()
        }
    }

}