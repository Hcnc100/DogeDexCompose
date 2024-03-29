//package com.nullpointer.dogedex.screens.cameraScreen
//
//import android.content.Context
//import androidx.camera.view.PreviewView
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.lifecycle.LifecycleOwner
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.nullpointer.dogedex.R
//import com.nullpointer.dogedex.domain.dogs.DogsRepository
//import com.nullpointer.dogedex.domain.ia.RecognitionRepository
//import com.nullpointer.dogedex.ia.DogRecognition
//import com.nullpointer.dogedex.models.dogs.data.DogData
//import com.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
//import com.nullpointer.dogedex.presentation.CameraViewModel
//import com.nullpointer.dogedex.ui.screen.camera.CameraScreen
//import com.nullpointer.dogedex.ui.screen.camera.test.CameraTestTag
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flowOf
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class CameraScreenTestDeny {
//
//    private class DogsFakeRepo : DogsRepository {
//        override val listDogs: Flow<List<DogData>> = flowOf(emptyList())
//        override val isFirstRequestCameraPermission: Flow<Boolean> = flowOf(true)
//
//        override suspend fun addDog(dogData: DogData) = Unit
//        override suspend fun refreshMyDogs() = Unit
//        override suspend fun firstRequestAllDogs() {
//            TODO("Not yet implemented")
//        }
//
//        override suspend fun changeIsFirstRequestCamera() = Unit
//        override suspend fun isNewDog(name: String): Boolean = true
//        override suspend fun getRecognizeDog(idRecognizeDog: String): DogData = DogData()
//
//    }
//
//    private class RecognitionFakeRepo : RecognitionRepository {
//        override suspend fun bindCameraToUseCases(
//            previewView: PreviewView,
//            lifecycleOwner: LifecycleOwner,
//            callbackRecognizeDog: (dog: DogRecognition, isConfidence: Boolean) -> Unit
//        ) = Unit
//
//        override fun clearCamera() = Unit
//
//    }
//
//
//    private var navController = DestinationsNavigatorImpl()
//    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
//
//    @get:Rule
//    var composeTestRule = createComposeRule()
//
//    @Test
//    fun showInterfaceWithOutPermission() {
//        val cameraViewModel = CameraViewModel(
//            dogsRepository = DogsFakeRepo(),
//            recognitionRepository = RecognitionFakeRepo()
//        )
//        composeTestRule.setContent {
//            CameraScreen(navigator = navController, cameraViewModel = cameraViewModel)
//        }
//        with(composeTestRule) {
//            // * show interface for get permission
//            onNodeWithTag(CameraTestTag.SCREEN_PERMISSION).assertIsDisplayed()
//            onNodeWithText(context.getString(R.string.message_permission_camera_is_necesary)).assertIsDisplayed()
//            onNodeWithText(context.getString((R.string.message_button_lauch_camera_permission))).assertIsEnabled()
//
//            // * show common interface
//            onNodeWithContentDescription(context.getString(R.string.description_button_collections)).assertIsDisplayed()
//            onNodeWithContentDescription(context.getString(R.string.description_button_settings)).assertIsDisplayed()
//
//            // * no show button capture image
//            onNodeWithContentDescription(context.getString(R.string.description_button_camera)).assertDoesNotExist()
//        }
//    }
//
//}