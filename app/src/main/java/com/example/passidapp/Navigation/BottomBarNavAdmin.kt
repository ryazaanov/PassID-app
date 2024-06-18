package com.example.passidapp.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.People
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
fun BottomNavigationAdmin(selectedItemIndex: Int, navController: NavController) {
    NavigationBar {
        val items = getItems1()
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

data class BottomNavItemAdmin(
    val title: String,
    val selectionIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

fun getItems1(): List<BottomNavItemAdmin> {
    return listOf(
        BottomNavItemAdmin(
            title = "Заявки",
            selectionIcon = Icons.Filled.Assignment,
            unselectedIcon = Icons.Outlined.Assignment,
            route = Screen.RequestScreen.route
        ),
        BottomNavItemAdmin(
            title = "Пропуска",
            selectionIcon = Icons.Filled.Badge,
            unselectedIcon = Icons.Outlined.Badge,
            route = Screen.PassAdminScreen.route
        ),
        BottomNavItemAdmin(
            title = "Пользователи",
            selectionIcon = Icons.Filled.People,
            unselectedIcon = Icons.Outlined.People,
            route = Screen.UserAdminScreen.route
        )
    )
}
