package com.nullpointer.dogedex.ui.screen.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.dogedex.core.states.Resource
import com.nullpointer.dogedex.models.profile.ProfileData
import com.nullpointer.dogedex.navigation.HomeNavGraph
import com.nullpointer.dogedex.ui.preview.provider.BooleanProvider
import com.nullpointer.dogedex.ui.screen.profile.components.ButtonLogOut
import com.nullpointer.dogedex.ui.screen.profile.components.ProfileInfo
import com.nullpointer.dogedex.ui.screen.profile.viewModel.ProfileViewModel
import com.nullpointer.dogedex.ui.share.BlockProcessing
import com.ramcosta.composedestinations.annotation.Destination

@HomeNavGraph
@Destination
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val stateUser by profileViewModel.profileData.collectAsState()
    val isProcessing = profileViewModel.isProcessing

    ProfileScreen(
        profileState = stateUser,
        isProcessing = isProcessing,
        logout = profileViewModel::signOut
    )
}


@Composable
fun ProfileScreen(
    logout: () -> Unit,
    isProcessing: Boolean,
    profileState: Resource<ProfileData>,
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ButtonLogOut(
                action = logout,
                isProcessing = isProcessing
            )
        }
    ) {
        when (profileState) {
            Resource.Failure -> Unit
            Resource.Loading -> BlockProcessing()
            is Resource.Success -> {
                ProfileInfo(
                    modifier = Modifier.padding(it),
                    profileData = profileState.data
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview(
    @PreviewParameter(BooleanProvider::class)
    isProcessing: Boolean = false
) {
    ProfileScreen(
        logout = { /*TODO*/ },
        isProcessing = isProcessing,
        profileState = Resource.Success(ProfileData.preview)
    )
}