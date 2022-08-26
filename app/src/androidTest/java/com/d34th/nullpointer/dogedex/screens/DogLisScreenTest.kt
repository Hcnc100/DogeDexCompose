package com.d34th.nullpointer.dogedex.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.navigation.DestinationsNavigatorImpl
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.ui.screen.listDogs.ListDogsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DogLisScreenTest {

    private class DogFakeRepository(
        val delayGetDogs: Long = 0,
        val launchErrorGetDogs: Boolean = false,
        val listDogsFake: List<Dog> = emptyList()
    ) : DogsRepository {

        companion object {
            fun generateListsDogs(numberDogsFake: Int, numberHasDog: Int = 0): List<Dog> {
                return when {
                    numberDogsFake <= 0 -> throw Exception("The number of dogs generated must be greater than zero")
                    numberHasDog < 0 -> throw Exception("The number of dog that has,  must be greater than zero")
                    numberHasDog > numberDogsFake -> throw Exception("The number of dogs generated must be greater than the number of dog that has")
                    numberHasDog == 0 -> (0 until numberDogsFake).map {
                        Dog(
                            index = it.toLong(),
                            id = it.toLong()
                        )
                    }
                    numberDogsFake == numberHasDog -> (0 until numberDogsFake).map {
                        Dog(index = it.toLong(), id = it.toLong(), hasDog = true, name = "dog-$it")
                    }
                    else -> {
                        val listFakeDogs =
                            (0 until numberDogsFake).map {
                                Dog(
                                    index = it.toLong(),
                                    id = it.toLong()
                                )
                            }
                                .toMutableList()
                        val listRandom = (0..numberDogsFake).asSequence()
                            .shuffled()
                            .take(numberHasDog)
                            .toList()
                        listRandom.forEach { index ->
                            listFakeDogs[index] =
                                listFakeDogs[index].copy(hasDog = true, name = "dog-$index")
                        }
                        listFakeDogs
                    }
                }

            }
        }

        override suspend fun getAllDogs(): Flow<List<Dog>> {
            if (delayGetDogs > 0) delay(delayGetDogs)
            return if (launchErrorGetDogs) throw Exception("Error") else flowOf(listDogsFake)
        }

        override suspend fun addDog(dog: Dog): ApiResponse<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun refreshMyDogs(): ApiResponse<Unit> {
            return ApiResponse.Success(Unit)
        }

        override suspend fun isNewDog(name: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun isFirstCameraRequest(): Flow<Boolean> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecognizeDog(idRecognizeDog: String): ApiResponse<Dog> {
            TODO("Not yet implemented")
        }

        override suspend fun changeIsFirstRequestCamera(isFirstRequest: Boolean) {
            TODO("Not yet implemented")
        }

    }


    private var navController = DestinationsNavigatorImpl()

    @get:Rule
    var composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun showShimmerLoading() {
        // * set delay for get a Loading resource
        val dogFakeRepo = DogFakeRepository(delayGetDogs = 10_000)
        val dogsViewModel = DogsViewModel(dogFakeRepo, SavedStateHandle())
        // * get number of items shimmer
        val numberItemsFake = context.resources.getInteger(R.integer.number_dog_loading)
        // * get any item between item start and last item
        // ? this indirect verify that exist all node items shimmer
        val randomShimmerTest = (1 until numberItemsFake - 1).random()
        composeTestRule.setContent {
            ListDogsScreen(navigator = navController, dogsViewModel = dogsViewModel)
        }
        with(composeTestRule.onNodeWithTag("screen-shimmer")) {
            // * test all list grid items loading
            assertIsDisplayed()
            // * test exist first item shimmer
            performScrollToKey(0)
            // * teste last item shimmer
            performScrollToKey(numberItemsFake - 1)
            // * test random item shimmer
            performScrollToKey(randomShimmerTest)
        }
    }

    @Test
    fun showMessageErrorDogsDatabase() = runTest {
        // * config the dog repository for launch error when get dogs
        val dogFakeRepo = DogFakeRepository(launchErrorGetDogs = true)
        val dogsViewModel = DogsViewModel(dogFakeRepo, SavedStateHandle())
        composeTestRule.setContent {
            ListDogsScreen(navigator = navController, dogsViewModel = dogsViewModel)
        }
        // * test show error message load my dogs
        composeTestRule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun showAllDogsShadow(): Unit = runTest {
        // * get a number of size for list dogs fake
        val randomDogs = (100..250).random()
        // * get a number for scroll to dog item
        // ? this indirect confirm that dog exist in this list
        val numberDogScrollTest = (10..25).random()
        // * create a fake data
        val dogFakeRepo =
            DogFakeRepository(listDogsFake = DogFakeRepository.generateListsDogs(randomDogs))
        val dogsViewModel = DogsViewModel(dogFakeRepo, SavedStateHandle())
        composeTestRule.setContent {
            ListDogsScreen(navigator = navController, dogsViewModel = dogsViewModel)
        }
        // * wait to success state dogs for view model
        // ? this confirm correctly functionality between viewModel and repository
        val result = dogsViewModel.stateListDogs.first { it is Resource.Success }
        if (result !is Resource.Success) throw Exception("Cast is impossible")
        with(composeTestRule.onNodeWithTag("screen-dogs")) {
            // * first check if the list is show
            assertExists()
            // * before scroll n times for item random for your key
            // * and verify if the node is
            repeat(numberDogScrollTest) {
                val dogItemRandom = result.data.random()
                performScrollToKey(dogItemRandom.id)
                composeTestRule.onNodeWithText(
                    context.getString(
                        R.string.name_shadow_dog,
                        dogItemRandom.index
                    )
                )
            }
        }
    }

    @Test
    fun showRandomDogsHave(): Unit = runTest {
        val randomDogs = (180..250).random()
        val randomHasDogs = (10..50).random()
        val testRandom = (10..50).random()
        val list = DogFakeRepository.generateListsDogs(randomDogs, randomHasDogs)
        val dogFakeRepo = DogFakeRepository(listDogsFake = list)
        val dogsViewModel = DogsViewModel(dogFakeRepo, SavedStateHandle())
        composeTestRule.setContent {
            ListDogsScreen(navigator = navController, dogsViewModel = dogsViewModel)
        }
        val result = dogsViewModel.stateListDogs.first { it is Resource.Success }
        if (result !is Resource.Success) throw Exception("Cast is impossible")

        // * repeat the test n twice
        repeat(testRandom) {
            // * get dog random
            val dogItemRandom = result.data.random()
            // * scroll to dog
            composeTestRule.onNodeWithTag("screen-dogs").performScrollToKey(dogItemRandom.id)
            if (!dogItemRandom.hasDog) {
                // * if don't has dog so, only show dog index's
                composeTestRule.onNodeWithText(
                    context.getString(
                        R.string.name_shadow_dog,
                        dogItemRandom.id
                    )
                ).assertExists()
            } else {
                // * if has dog so, show image with description with her name
                composeTestRule.onNodeWithContentDescription(
                    context.getString(
                        R.string.description_img_dog,
                        dogItemRandom.name
                    )
                ).assertExists()
            }
        }
    }

}