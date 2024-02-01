package com.d34th.nullpointer.dogedex.datasource.settings.local

import com.d34th.nullpointer.dogedex.models.settings.data.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
     val isFirstLoadingUser: Flow<Boolean>
     val isFirstRequestCameraPermission: Flow<Boolean>
    val settingsData: Flow<SettingsData?>

    suspend fun clearDataSaved()
    suspend fun changeIsFirstRequestCamera()
    suspend fun changeIsFirstLoginUser()
}