package com.d34th.nullpointer.dogedex

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToKey
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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


private class DogFakeRepository(
    val delayGetDogs: Long = 0,
    val launchErrorGetDogs: Boolean = false,
    val listDogsFake: List<Dog> = emptyList()
) : DogsRepository {

    companion object {
        fun generateListsDogs(numberDogsFake: Int, numberHasDog: Int = 0): List<Dog> {
            val listFakeDogs =
                (0 until numberDogsFake).map { Dog(index = it.toLong(), id = it.toLong()) }
                    .toMutableList()
            return when {
                numberHasDog == 0 -> listFakeDogs
                else -> {
                    val listRandom = (0..numberDogsFake).asSequence()
                        .shuffled()
                        .take(numberHasDog)
                        .toList()

                    listRandom.forEach { index ->
                        listFakeDogs[index] = listFakeDogs[index].copy(hasDog = true)
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DogLisScreenTest {

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
            // * before scroll n twice for item random for your key
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
        val list = DogFakeRepository.generateListsDogs(10, 3)
        val dogFakeRepo =
            DogFakeRepository(listDogsFake = emptyList())
        val dogsViewModel = DogsViewModel(dogFakeRepo, SavedStateHandle())
        composeTestRule.setContent {
            ListDogsScreen(navigator = navController, dogsViewModel = dogsViewModel)
        }
        list
    }

}