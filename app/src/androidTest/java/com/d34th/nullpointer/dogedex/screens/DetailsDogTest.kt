package com.d34th.nullpointer.dogedex.screens

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.screen.details.DogDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailsDogTest {

    private class DogFakeRepository : DogsRepository {
        override val listDogs: Flow<List<Dog>> = flowOf(emptyList())
        override val isFirstRequestCameraPermission: Flow<Boolean> = flowOf(true)

        override suspend fun addDog(dog: Dog) = Unit
        override suspend fun refreshMyDogs() = Unit
        override suspend fun firstRequestAllDogs() = Unit
        override suspend fun changeIsFirstRequestCamera() = Unit
        override suspend fun isNewDog(name: String): Boolean = true
        override suspend fun getRecognizeDog(idRecognizeDog: String): Dog = Dog()

    }

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val navigator: DestinationsNavigatorImpl = DestinationsNavigatorImpl()

    @Test
    fun showInterfaceIfIsNewDog() {
        val dogTest = Dog(index = 0, name = "Test dog")
        composeTestRule.setContent {
            DogDetails(
                dog = dogTest,
                isNewDog = true,
                navigator = navigator,
                dogsViewModel = DogsViewModel(DogFakeRepository(), SavedStateHandle())
            )
        }
        with(composeTestRule) {

            // * show correct img with description unique
            onNodeWithContentDescription(
                context.getString(
                    R.string.description_has_dog,
                    dogTest.index,
                    dogTest.name
                )
            ).assertExists()

            // * show correct info dog
            onNodeWithText("Test dog").assertIsDisplayed()
            onNodeWithText(context.getString(R.string.index_dog, dogTest.index)).assertIsDisplayed()

            // * show title new dog
            onNodeWithText(context.getString(R.string.title_details_new_dog)).assertIsDisplayed()
            // * show button save dog
            onNodeWithText(context.getString(R.string.text_save_dog)).assertIsDisplayed()
            onNodeWithContentDescription(context.getString(R.string.description_button_save_dog)).assertIsDisplayed()
        }
    }

    @Test
    fun showInterfacesIfHasDog() {
        val dogTest = Dog(index = 10, name = "Test dog")
        composeTestRule.setContent {
            DogDetails(
                dog = dogTest,
                isNewDog = false,
                navigator = navigator,
                dogsViewModel = DogsViewModel(DogFakeRepository(), SavedStateHandle())
            )
        }
        with(composeTestRule) {
            // * show text info dog
            onNodeWithText(context.getString(R.string.title_details_saved_dog)).assertIsDisplayed()
            // * no show button save
            onNodeWithText(context.getString(R.string.description_button_save_dog)).assertDoesNotExist()
        }
    }


}