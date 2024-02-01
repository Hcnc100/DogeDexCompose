package com.nullpointer.dogedex.navigation

import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.ui.screen.destinations.CameraScreenDestination
import com.nullpointer.dogedex.ui.screen.destinations.DirectionDestination
import com.nullpointer.dogedex.ui.screen.destinations.ListDogsScreenDestination
import com.nullpointer.dogedex.ui.screen.destinations.ProfileScreenDestination

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
    Profile(
        label = R.string.settings,
        icon = R.drawable.person,
        destination = ProfileScreenDestination
    )
}