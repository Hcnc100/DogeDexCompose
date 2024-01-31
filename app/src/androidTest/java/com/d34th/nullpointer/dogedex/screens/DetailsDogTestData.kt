package com.d34th.nullpointer.dogedex.screens

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailsDogTestData {

    private class DogDataFakeRepository : DogsRepository {
        override val listDogs: Flow<List<DogData>> = flowOf(emptyList())
        override val isFirstRequestCameraPermission: Flow<Boolean> = flowOf(true)

        override suspend fun addDog(dogData: DogData) = Unit
        override suspend fun refreshMyDogs() = Unit
        override suspend fun changeIsFirstRequestCamera() = Unit


        override suspend fun getRecognizeDog(dogRecognition: DogRecognition): DogData {
            TODO("Not yet implemented")
        }


    }

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val navigator: DestinationsNavigatorImpl = DestinationsNavigatorImpl()

    @Test
    fun showInterfaceIfIsNewDog() {
//        val dogDataTest = DogData(id = 1, name = "Test dog")
//        composeTestRule.setContent {
//            DogDetails(
//                dogData = dogDataTest,
//                isNewDog = true,
//                navigator = navigator,
//                dogsViewModel = DogsViewModel(DogDataFakeRepository(), SavedStateHandle())
//            )
//        }
//        with(composeTestRule) {
//
//            // * show correct img with description unique
//            onNodeWithContentDescription(
//                context.getString(
//                    R.string.description_has_dog,
//                    dogDataTest.index,
//                    dogDataTest.name
//                )
//            ).assertExists()
//
//            // * show correct info dog
//            onNodeWithText("Test dog").assertIsDisplayed()
//            onNodeWithText(
//                context.getString(
//                    R.string.index_dog,
//                    dogDataTest.index
//                )
//            ).assertIsDisplayed()
//
//            // * show title new dog
//            onNodeWithText(context.getString(R.string.title_details_new_dog)).assertIsDisplayed()
//            // * show button save dog
//            onNodeWithText(context.getString(R.string.text_save_dog)).assertIsDisplayed()
//            onNodeWithContentDescription(context.getString(R.string.description_button_save_dog)).assertIsDisplayed()
//        }
//    }

        @Test
        fun showInterfacesIfHasDog() {
//        val dogDataTest = DogData(id = 10, name = "Test dog")
//        composeTestRule.setContent {
//            DogDetails(
//                dogData = dogDataTest,
//                isNewDog = false,
//                navigator = navigator,
//                dogsViewModel = DogsViewModel(DogDataFakeRepository(), SavedStateHandle())
//            )
//        }
//        with(composeTestRule) {
//            // * show text info dog
//            onNodeWithText(context.getString(R.string.title_details_saved_dog)).assertIsDisplayed()
//            // * no show button save
//            onNodeWithText(context.getString(R.string.description_button_save_dog)).assertDoesNotExist()
//        }
        }


}
}