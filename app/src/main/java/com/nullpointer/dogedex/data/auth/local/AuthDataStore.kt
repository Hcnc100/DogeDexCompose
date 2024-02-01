package com.nullpointer.dogedex.data.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nullpointer.dogedex.models.auth.data.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataStore(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private const val KEY_AUTH_DATA_ID = "KEY_AUTH_DATA_ID"
    }

    private val keyAuthData = stringPreferencesKey(KEY_AUTH_DATA_ID)


    fun getAuthData(): Flow<AuthData?> = dataStore.data.map { pref ->
        pref[keyAuthData]?.let { Json.decodeFromString(it) }
    }

    suspend fun updateAuthData(authData: AuthData){
        dataStore.edit { pref ->
            pref[keyAuthData] = Json.encodeToString(authData)
        }
    }

    suspend fun clearAuthData(){
        dataStore.edit { pref ->
            pref.remove(keyAuthData)
        }
    }
}