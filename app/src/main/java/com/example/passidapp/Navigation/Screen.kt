package com.example.passidapp.Navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object NotificationScreen : Screen("notification_screen")
//    object NotificationInfoScreen : Screen("notification_info_screen")
    object ProfileScreen : Screen("profile_screen")
    object PassScreen : Screen("pass_screen/{pass_id}") {
        fun createRoute(pass_id: String) = "pass_screen/$pass_id"
    }
    object UserAdminScreen : Screen("user_screen")
    object UserDetailScreen : Screen("user_screen/{user_id}") {
        fun createRoute(user_id: String) = "user_screen/$user_id"
    }
    object UpdateUserScreen : Screen("update_user_screen/{user_id}") {
        fun createRoute(user_id: String) = "update_user_screen/$user_id"
    }
    object UpdatePassScreen : Screen("update_pass_screen/{pass_id}") {
        fun createRoute(pass_id: String) = "update_pass_screen/$pass_id"
    }
    object PassScreenAdmin : Screen("pass_screen_admin/{pass_id}") {
        fun createRoute(pass_id: String) = "pass_screen_admin/$pass_id"
    }
    object QRScannerScreen : Screen("qr_scanner_screen")
    object RequestScreen : Screen("request_screen")
    object PassAdminScreen : Screen("pass_admin_screen")
    object AccessHistoryScreen : Screen("access_log_screen")
    object PassTypeScreen : Screen("pass_type_screen")
    object CreatePassScreen : Screen("create_pass_screen")
    object CreateUserScreen : Screen("create_user_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
}
