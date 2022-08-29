package com.d34th.nullpointer.dogedex.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.utils.ExceptionManager
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import com.d34th.nullpointer.dogedex.presentation.AuthViewModel
import com.d34th.nullpointer.dogedex.ui.screen.login.LoginScreen
import com.d34th.nullpointer.dogedex.ui.screen.login.test.LoginTestTags
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LoginScreenScreenTest {
    private class AuthRepoFake(
        val exceptionMessage: String = ""
    ) : AuthRepository {

        private val _currentUser = MutableStateFlow(User())

        override val currentUser: Flow<User> = _currentUser.asStateFlow()
        override val isAuthUser: Flow<Boolean> = flowOf(false)

        override suspend fun signIn(userCredentials: SignInDTO) {
            if (exceptionMessage.isNotEmpty()) {
                throw Exception(exceptionMessage)
            } else {
                delay(2_000)
                _currentUser.value = User(
                    id = 12345,
                    email = userCredentials.email,
                    token = "fakeToken"
                )
            }
        }

        override suspend fun signUp(userCredentials: SignUpDTO) = Unit
        override suspend fun signOut() = Unit

    }

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val navigator: DestinationsNavigatorImpl = DestinationsNavigatorImpl()


    @Test
    fun validateTextEmptySignIn() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthRepoFake()
        )
        composeTestRule.setContent {
            LoginScreen(authViewModel = authViewModel, navigator = navigator)
        }
        with(composeTestRule) {

            // * check show is fields empty
            onNodeWithText(context.getString((R.string.text_button_sign_in))).performClick()

            // * show text error in inputs
            onNodeWithText(context.getString(R.string.error_empty_email)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.error_empty_password)).assertIsDisplayed()

            // * show message error snack
            onNodeWithText(context.getString(R.string.error_data_invalid)).assertIsDisplayed()
        }
    }

    @Test
    fun validateTextLongSignIn() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthRepoFake()
        )
        composeTestRule.setContent {
            LoginScreen(authViewModel = authViewModel, navigator = navigator)
        }
        with(composeTestRule) {

            // * add very long text
            onNodeWithTag("input-email").performTextInput("rdtgfdgsdfsdfgsgfsdgfsdgsdgsgfgfdsgs@gmaisfasdfasdfasfdasdffadl.com")
            onNodeWithTag("input-password").performTextInput("anypasswor0dasdfasfdfasfdasdfasfretgrehehythtyhjeytjhetjhetjhe")

            // * show text error in inputs
            onNodeWithText(context.getString(R.string.error_length_email)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.error_length_password)).assertIsDisplayed()

            onNodeWithText(context.getString((R.string.text_button_sign_in))).performClick()

            // * show message error snack
            onNodeWithText(context.getString(R.string.error_data_invalid)).assertIsDisplayed()
        }
    }


    @Test
    fun validateTextInvalidSignIn() {
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthRepoFake()
        )
        composeTestRule.setContent {
            LoginScreen(authViewModel = authViewModel, navigator = navigator)
        }
        with(composeTestRule) {

            // * add invalid email
            onNodeWithTag(LoginTestTags.INPUT_EMAIL).performTextInput("invalid email")
            onNodeWithTag(LoginTestTags.INPUT_PASSWORD).performTextInput("anypasswor0d")

            onNodeWithText(context.getString((R.string.text_button_sign_in))).performClick()

            // * validate error text email
            onNodeWithText(context.getString(R.string.error_valid_email)).assertIsDisplayed()

            // * show message error snack
            onNodeWithText(context.getString(R.string.error_data_invalid)).assertIsDisplayed()

        }
    }

    @Test
    fun errorCredentials() = runTest {
        // * add exception error
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = AuthRepoFake(
                exceptionMessage = ExceptionManager.USER_NOT_FOUND
            )
        )
        composeTestRule.setContent {
            LoginScreen(authViewModel = authViewModel, navigator = navigator)
        }
        with(composeTestRule) {

            // * add inputs
            onNodeWithTag(LoginTestTags.INPUT_EMAIL).performTextInput("generic.email@gmail.com")
            onNodeWithTag(LoginTestTags.INPUT_PASSWORD).performTextInput("anypasswor0d")

            onNodeWithText(context.getString((R.string.text_button_sign_in))).performClick()

            delay(2_500)

            // * show correct error
            onNodeWithText(context.getString(R.string.error_user_no_register)).assertIsDisplayed()
        }
    }

    @Test
    fun showProgressIndicator() = runTest {
        val repoFake = AuthRepoFake()
        val authViewModel = AuthViewModel(
            savedStateHandle = SavedStateHandle(),
            authRepo = repoFake
        )
        composeTestRule.setContent {
            LoginScreen(authViewModel = authViewModel, navigator = navigator)
        }
        with(composeTestRule) {

            // * fill in fields
            onNodeWithTag(LoginTestTags.INPUT_EMAIL).performTextInput("generic.email@gmail.com")
            onNodeWithTag(LoginTestTags.INPUT_PASSWORD).performTextInput("anypasswor0d")

            onNodeWithText(context.getString((R.string.text_button_sign_in))).performClick()

            // * show circulate progress indicator
            onNodeWithTag(LoginTestTags.INDICATOR_PROGRESS).assertExists()
        }
    }


}