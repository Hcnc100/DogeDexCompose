package com.nullpointer.dogedex.tests.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.core.states.Resource
import com.nullpointer.dogedex.core.utils.UtilsFake
import com.nullpointer.dogedex.domain.dogs.DogsRepository
import com.nullpointer.dogedex.models.dogs.data.DogData
import com.nullpointer.dogedex.presentation.DogsViewModel
import com.nullpointer.dogedex.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DogsViewModelTest {

    private class DogFakeRepoImpl(
        private val delayRequestDog: Long = 2_000,
        private val launchErrorGetDogs: Boolean = false,
        val listFakeDogData: List<DogData> = emptyList()
    ) : DogsRepository {
        override val listDogs: Flow<List<DogData>> = flow {
            delay(delayRequestDog)
            emit(listFakeDogData)
        }
        override val isFirstRequestCameraPermission: Flow<Boolean> = flowOf(true)
        override suspend fun refreshMyDogs() {
            delay(delayRequestDog)
            if (launchErrorGetDogs) throw Exception("Random exception")
        }

        override suspend fun firstRequestAllDogs() = Unit
        override suspend fun getRecognizeDog(idRecognizeDog: String): DogData = DogData()
        override suspend fun addDog(dogData: DogData) = Unit
        override suspend fun isNewDog(name: String): Boolean = true
        override suspend fun changeIsFirstRequestCamera() = Unit
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `Check change state for request my dogs manually`() =
        runTest(mainCoroutineRule.dispatcher) {
            val dogsViewModel = DogsViewModel(
                dogsRepository = DogFakeRepoImpl(),
                savedStateHandle = SavedStateHandle()
            )
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
        val dogsViewModel = DogsViewModel(
            dogsRepository = DogFakeRepoImpl(),
            savedStateHandle = SavedStateHandle()
        )
        // * get only 2 states, Loading and Success
        dogsViewModel.stateListDogs.take(2).collectIndexed { index, value ->
            if (index == 0) assert(value is Resource.Loading)
            if (index == 1) assert(value is Resource.Success)
        }
    }

    @Test
    fun `Test get all dogs for repository`() = runTest(mainCoroutineRule.dispatcher) {
        val listFakeDogs = UtilsFake.generateListsDogs(10)
        val dogsViewModel = DogsViewModel(
            DogFakeRepoImpl(listFakeDogData = listFakeDogs),
            SavedStateHandle()
        )
        val listDogs = dogsViewModel.stateListDogs.first {
            it is Resource.Success && it.data.isNotEmpty()
        } as Resource.Success<List<DogData>>
        assert(listDogs.data.size == listFakeDogs.size)
    }

    @Test
    fun `Launch Error request my dogs and no correctly collect error`() =
        runTest(mainCoroutineRule.dispatcher) {
            val dogsViewModel = DogsViewModel(
                dogsRepository = DogFakeRepoImpl(
                    launchErrorGetDogs = true
                ),
                savedStateHandle = SavedStateHandle(),
            )
            val message = dogsViewModel.messageDogs.first()
            assert(message == R.string.error_unknown)
        }

}
