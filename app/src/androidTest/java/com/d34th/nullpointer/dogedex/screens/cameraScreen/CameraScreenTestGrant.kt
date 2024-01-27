package com.d34th.nullpointer.dogedex.screens.cameraScreen

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.LifecycleOwner
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.domain.ia.RecognitionRepository
import com.d34th.nullpointer.dogedex.ia.DogRecognition
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import com.d34th.nullpointer.dogedex.presentation.CameraViewModel
import com.d34th.nullpointer.dogedex.ui.screen.camera.CameraScreen
import com.d34th.nullpointer.dogedex.ui.screen.camera.test.CameraTestTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CameraScreenTestGrant {
    private class DogsFakeRepo : DogsRepository {
        override val listDogs: Flow<List<Dog>> = flowOf(emptyList())
        override val isFirstRequestCameraPermission: Flow<Boolean> = flowOf(true)

        override suspend fun addDog(dog: Dog) = Unit
        override suspend fun refreshMyDogs() = Unit
        override suspend fun firstRequestAllDogs() {
            TODO("Not yet implemented")
        }

        override suspend fun changeIsFirstRequestCamera() = Unit
        override suspend fun isNewDog(name: String): Boolean = true
        override suspend fun getRecognizeDog(idRecognizeDog: String): Dog = Dog()

    }

    private class RecognitionFakeRepo : RecognitionRepository {
        override suspend fun bindCameraToUseCases(
            previewView: PreviewView,
            lifecycleOwner: LifecycleOwner,
            callbackRecognizeDog: (dog: DogRecognition, isConfidence: Boolean) -> Unit
        ) = Unit

        override fun clearCamera() = Unit

    }


    private var navController = DestinationsNavigatorImpl()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA
    )

    @Test
    fun showInterfaceGrandPermission() {
        val cameraViewModel = CameraViewModel(
            dogsRepository = DogsFakeRepo(),
            recognitionRepository = RecognitionFakeRepo()
        )
        composeTestRule.setContent {
            CameraScreen(navigator = navController, cameraViewModel = cameraViewModel)
        }
        with(composeTestRule) {
            // * show interface for get permission
            onNodeWithTag(CameraTestTag.SCREEN_CAPTURE_IMAGE).assertIsDisplayed()

            // * show interface capture img
            onNodeWithContentDescription(context.getString(R.string.description_button_collections)).assertIsDisplayed()
            onNodeWithContentDescription(context.getString(R.string.description_button_settings)).assertIsDisplayed()
            onNodeWithContentDescription(context.getString(R.string.description_button_camera)).assertIsDisplayed()


        }
    }
}