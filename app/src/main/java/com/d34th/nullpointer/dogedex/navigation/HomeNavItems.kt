package com.d34th.nullpointer.dogedex.navigation

import com.d34th.nullpointer.dogedex.R
import com.d34th.nullpointer.dogedex.ui.screen.destinations.CameraScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.DirectionDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.ListDogsScreenDestination
import com.d34th.nullpointer.dogedex.ui.screen.destinations.SettingsScreenDestination

enum class HomeNavItems(
    val label: Int,
    val icon: Int,
    val destination: DirectionDestination
) {
    DogList(
        label = R.string.dog_list,
        icon = R.drawable.pets,
        destination = ListDogsScreenDestination
    ),
    Camera(
        label = R.string.camera,
        icon = R.drawable.ic_camera,
        destination = CameraScreenDestination
    ),
    Settings(
        label = R.string.settings,
        icon = R.drawable.ic_settings,
        destination = SettingsScreenDestination
    )
}