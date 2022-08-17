package com.d34th.nullpointer.dogedex.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.d34th.nullpointer.dogedex.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefsUser(val context: Context) {

    companion object {
        private const val NAME_PREF_USER = "DOGE_DEX_PREF"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_USER_ID = "KEY_USER_ID"
        private const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
        private const val KEY_FIRST_LOGIN = "KEY_FIRST_LOGIN"
        private const val KEY_REQUEST_CAMERA = "KEY_REQUEST_CAMERA"
    }

    private val keyEmailUser = stringPreferencesKey(KEY_USER_NAME)
    private val keyTokenUser = stringPreferencesKey(KEY_USER_TOKEN)
    private val keyIdUser = longPreferencesKey(KEY_USER_ID)
    private val keyIsFirstLogin = booleanPreferencesKey(KEY_FIRST_LOGIN)
    private val keyRequestCamera = booleanPreferencesKey(KEY_REQUEST_CAMERA)

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME_PREF_USER)

    fun getUser(): Flow<User> = context.dataStore.data.map { pref ->
        User(
            id = pref[keyIdUser] ?: -1,
            email = pref[keyEmailUser] ?: "",
            token = pref[keyTokenUser] ?: "",
        )
    }

    fun getIsFirstLogin(): Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[keyIsFirstLogin] ?: true
    }

    suspend fun changeIsFirstLogin(isFirstLogin: Boolean) = context.dataStore.edit { pref ->
        pref[keyIsFirstLogin] = isFirstLogin
    }

    suspend fun changeUser(user: User) = context.dataStore.edit { pref ->
        pref[keyIdUser] = user.id
        pref[keyEmailUser] = user.email
        pref[keyTokenUser] = user.token
    }

    suspend fun deleteUser() = context.dataStore.edit { pref ->
        pref.clear()
    }

    fun getIsFirstCameraRequest(): Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[keyRequestCamera] ?: true
    }

    suspend fun changeIsFirstCameraRequest(isFirstRequest: Boolean) =
        context.dataStore.edit { pref ->
            pref[keyRequestCamera] = isFirstRequest
        }


}