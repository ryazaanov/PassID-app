package com.example.passidapp.Navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object NotificationScreen : Screen("notification_screen")
    object NotificationInfoScreen : Screen("notification_info_screen")
    object ProfileScreen : Screen("profile_screen")
    object PassScreen : Screen("pass_screen")
    object PassTypeScreen : Screen("pass_type_screen")
    object CreatePassScreen : Screen("create_pass_screen")
}