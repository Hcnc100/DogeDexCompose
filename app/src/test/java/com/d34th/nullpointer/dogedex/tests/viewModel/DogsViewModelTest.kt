package com.d34th.nullpointer.dogedex.tests.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog
import com.d34th.nullpointer.dogedex.presentation.DogsViewModel
import com.d34th.nullpointer.dogedex.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


private class DogFakeRepoImpl(
    private val numberFakeDogs: Int = 5,
    private val delayRequestDog: Long = 5_000,
    private val launchError: Boolean = false
) : DogsRepository {

    private val fakeListDogs = MutableStateFlow(emptyList<Dog>())

    override suspend fun getAllDogs(): Flow<List<Dog>> = fakeListDogs.asStateFlow()

    override suspend fun refreshMyDogs(): ApiResponse<Unit> {
        delay(delayRequestDog)
        fakeListDogs.value = (0 until numberFakeDogs).map { Dog(index = it.toLong()) }
        return if (launchError) ApiResponse.Failure("Error request") else ApiResponse.Success(Unit)
    }

    override suspend fun addDog(dog: Dog): ApiResponse<Unit> {
        TODO("Not yet implemented")
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
@RunWith(JUnit4::class)
class DogsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `Check change state for request my dogs manually`() =
        runTest(mainCoroutineRule.dispatcher) {
            val dogsViewModel = DogsViewModel(DogFakeRepoImpl(), SavedStateHandle())
            // * check init state
            assert(!dogsViewModel.isLoadingMyGogs)
            // * launch request
            val response = dogsViewModel.requestMyLastDogs()
            // * advance 2 seconds
            advanceTimeBy(2_000)
            // * check the new state
            assert(dogsViewModel.isLoadingMyGogs)
            // * await the finish task and check the new state
            response.join()
            assert(!dogsViewModel.isLoadingMyGogs)
        }

    @Test
    fun `Test launch request dog when init view model`() = runTest(mainCoroutineRule.dispatcher) {
        val dogsViewModel = DogsViewModel(DogFakeRepoImpl(), SavedStateHandle())
        // * get only 2 states, Loading and Success
        dogsViewModel.stateListDogs.take(2).collectIndexed { index, value ->
            if (index == 0) assert(value is Resource.Loading)
            if (index == 1) assert(value is Resource.Success)
        }
    }

    @Test
    fun `Test get all dogs for repository`() = runTest(mainCoroutineRule.dispatcher) {
        val sizeFakeDog = 10
        val dogsViewModel = DogsViewModel(DogFakeRepoImpl(sizeFakeDog), SavedStateHandle())
        val listDogs = dogsViewModel.stateListDogs.first {
            it is Resource.Success && it.data.isNotEmpty()
        } as Resource.Success<List<Dog>>
        assert(listDogs.data.size == sizeFakeDog)
    }

    @Test
    fun `Launch Error request my dogs and no correctly collect error`() =
        runTest(mainCoroutineRule.dispatcher) {
            val dogsViewModel =
                DogsViewModel(DogFakeRepoImpl(launchError = true), SavedStateHandle())
            val message = dogsViewModel.messageDogs.first()
            assert(message.isNotEmpty())
        }

}
