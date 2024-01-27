package com.d34th.nullpointer.dogedex.datasource.settings.local

import com.d34th.nullpointer.dogedex.data.settings.SettingsDataStore
import com.d34th.nullpointer.dogedex.models.settings.data.SettingsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsLocalDataSourceImpl(
    private val settingsDataStore: SettingsDataStore,
) : SettingsLocalDataSource {
    override val isFirstLoadingUser: Flow<Boolean> = settingsDataStore.getSettingsData().map {
        it?.isFirstLogin ?: true
    }
    override val isFirstRequestCameraPermission: Flow<Boolean> =
        settingsDataStore.getSettingsData().map {
            it?.isFirstRequestCameraPermission ?: true
        }
    override val settingsData: Flow<SettingsData?> = settingsDataStore.getSettingsData()

    override suspend fun changeIsFirstRequestCamera(){
        val settingsDataUpdate = (settingsData.first() ?: SettingsData()).copy(
            isFirstRequestCameraPermission = false
        )
        settingsDataStore.updateSettingsData(settingsDataUpdate)
    }

    override suspend fun changeIsFirstLoginUser() {
        val settingsDataUpdate = (settingsData.first() ?: SettingsData()).copy(
            isFirstLogin = false
        )
        settingsDataStore.updateSettingsData(settingsDataUpdate)
    }


    override suspend fun clearDataSaved() =
        settingsDataStore.clearSettingsData()

}