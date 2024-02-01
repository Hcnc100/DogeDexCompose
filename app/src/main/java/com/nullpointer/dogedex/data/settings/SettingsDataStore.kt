package com.nullpointer.dogedex.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nullpointer.dogedex.models.settings.data.SettingsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
) {
    companion object{
        private const val KEY_SETTINGS="KEY_SETTINGS"
    }

    private val keySettings=stringPreferencesKey(KEY_SETTINGS)

    fun getSettingsData():Flow<SettingsData?> = dataStore.data.map { pref ->
        pref[keySettings]?.let { Json.decodeFromString(it) }
    }

    suspend fun updateSettingsData(settingsData: SettingsData){
        dataStore.edit { pref ->
            pref[keySettings] = Json.encodeToString(settingsData)
        }
    }

    suspend fun clearSettingsData(){
        dataStore.edit { pref ->
            pref.remove(keySettings)
        }
    }
}