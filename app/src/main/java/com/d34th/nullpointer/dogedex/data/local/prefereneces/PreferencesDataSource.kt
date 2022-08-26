package com.d34th.nullpointer.dogedex.data.local.prefereneces

import com.d34th.nullpointer.dogedex.models.User
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    val currentUser: Flow<User>
    val isFirstLoadingUser: Flow<Boolean>
    val isFirstRequestCameraPermission: Flow<Boolean>

    suspend fun updateCurrentUser(user: User)
    suspend fun changeFirstRequestCameraPermission()
    suspend fun changeIsFirstLoginUser()
    suspend fun clearDataSaved()
}