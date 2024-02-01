package com.d34th.nullpointer.dogedex.ui.screen.profile.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.core.utils.launchSafeIO
import com.d34th.nullpointer.dogedex.domain.auth.AuthRepository
import com.d34th.nullpointer.dogedex.domain.dogs.DogsRepository
import com.d34th.nullpointer.dogedex.models.auth.data.AuthData
import com.d34th.nullpointer.dogedex.models.profile.ProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dogsRepository: DogsRepository
) : ViewModel() {

    var isProcessing by mutableStateOf(false)
        private set

    val profileData = combine<AuthData?, Int, Resource<ProfileData>>(
        authRepository.currentUser,
        dogsRepository.dogsCaught
    ) { authData, dogsCaught ->
        Resource.Success(
            ProfileData(
                dogsCaught = dogsCaught,
                email = authData?.email ?: "",
            )
        )
    }.catch {
        emit(Resource.Failure)
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resource.Loading
        )

    fun signOut() = launchSafeIO(
        blockBefore = { isProcessing = true },
        blockAfter = { isProcessing = false },
        blockIO = { authRepository.signOut() }
    )

}

