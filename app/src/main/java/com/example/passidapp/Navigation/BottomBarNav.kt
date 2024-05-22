package com.example.passidapp.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(selectedItemIndex: Int, navController: NavController) {
    NavigationBar {
        val items = getitmes()
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectionIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )

                }
            )
        }
    }
}


data class BottomNavItem(
    val title: String,
    val selectionIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

fun getitmes():List<BottomNavItem> {
    return listOf(
        BottomNavItem(
            title = "Уведомления",
            selectionIcon = Icons.Filled.Notifications,
            unselectedIcon = Icons.Outlined.Notifications,
            route = Screen.NotificationScreen.route
        ),
        BottomNavItem(
            title = "Главная",
            selectionIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.HomeScreen.route
        ),
        BottomNavItem(
            title = "Еще",
            selectionIcon = Icons.Filled.Menu,
            unselectedIcon = Icons.Outlined.Menu,
            route = Screen.ProfileScreen.route
        ),
    )
}