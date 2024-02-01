package com.nullpointer.dogedex.models.settings.data

import kotlinx.serialization.Serializable

@Serializable
data class SettingsData(
    val isFirstRequestCameraPermission: Boolean = true,
    val isFirstLogin: Boolean = true
)
