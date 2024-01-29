package com.d34th.nullpointer.dogedex.ui.screen.home.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.d34th.nullpointer.dogedex.navigation.HomeNavItems
import com.d34th.nullpointer.dogedex.ui.screen.appCurrentDestinationAsState
import com.d34th.nullpointer.dogedex.ui.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun HomeBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentDestination = navController.appCurrentDestinationAsState().value

    BottomNavigation(
        modifier = modifier,
    ) {
        HomeNavItems.entries.map { destination ->
            BottomNavigationItem(
                alwaysShowLabel = true,
                label = {
                    Text(text = stringResource(id = destination.label))
                },
                selected = currentDestination == destination.destination,
                onClick = {
                    navController.navigate(destination.destination) {
                        popUpTo(HomeScreenDestination.route) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(destination.icon),
                        contentDescription = stringResource(id = destination.label)
                    )
                }
            )
        }
    }
}