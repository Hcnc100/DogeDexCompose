package com.d34th.nullpointer.dogedex.screens

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import com.d34th.nullpointer.dogedex.ui.screen.details.DogDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailsDogTest {

    private class DogFakeRepository(
        val launchErrorSaveDog: Boolean,
    ) : DogsRepository {
        override val listDogs: Flow<List<Dog>> = flowOf(emptyList())
        override val isFirstRequestCameraPermission: Flow<Boolean> = flowOf(true)

        override suspend fun addDog(dog: Dog) = Unit
        override suspend fun refreshMyDogs() = Unit
        override suspend fun changeIsFirstRequestCamera() = Unit
        override suspend fun isNewDog(name: String): Boolean = true
        override suspend fun getRecognizeDog(idRecognizeDog: String): Dog = Dog()

    }

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun showInterfaceIfIsNewDog() {
        val navigator = DestinationsNavigatorImpl()
        val dogTest = Dog(index = 0, name = "Dog Test")
        composeTestRule.setContent {
            DogDetails(dog = dogTest, isNewDog = true, navigator = navigator)
        }
    }


}