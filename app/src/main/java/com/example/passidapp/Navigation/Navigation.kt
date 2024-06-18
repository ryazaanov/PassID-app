package com.example.passidapp.Navigation

import CreatePassScreen
import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.passidapp.views.AccessHistoryScreen
import com.example.passidapp.views.CreateUserScreen
import com.example.passidapp.views.HomeScreen
import com.example.passidapp.views.NotificationScreen
import com.example.passidapp.views.PassAdminScreen
import com.example.passidapp.views.PassScreen
import com.example.passidapp.views.PassScreenAdmin
import com.example.passidapp.views.PassTypeScreen
import com.example.passidapp.views.ProfileScreen
import com.example.passidapp.views.QRScannerScreen
import com.example.passidapp.views.RegisterScreen
import com.example.passidapp.views.RequestScreen
import com.example.passidapp.views.UpdatePassScreen
import com.example.passidapp.views.UpdateUserScreen
import com.example.passidapp.views.UserAdminScreen
import com.example.passidapp.views.UserScreen

@Composable
fun Navigation(isAuthenticated: Boolean) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = if (isAuthenticated) Screen.HomeScreen.route else Screen.LoginScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.PassScreen.route,
            arguments = listOf(navArgument("pass_id") { type = NavType.StringType })
        ) { backStackEntry ->
            val pass_id = backStackEntry.arguments?.getString("pass_id") ?: return@composable
            PassScreen(navController, pass_id)
        }
        composable(
            route = Screen.PassScreenAdmin.route,
            arguments = listOf(navArgument("pass_id") { type = NavType.StringType })
        ) { backStackEntry ->
            val pass_id = backStackEntry.arguments?.getString("pass_id") ?: return@composable
            PassScreenAdmin(navController, pass_id)
        }
        composable(route = Screen.PassTypeScreen.route) {
            PassTypeScreen(navController)
        }
        composable(route = Screen.CreatePassScreen.route) {
            CreatePassScreen(navController)
        }
        composable(route = Screen.QRScannerScreen.route) {
            QRScannerScreen(navController)
        }
        composable(
            route = Screen.UpdatePassScreen.route,
            arguments = listOf(navArgument("pass_id") { type = NavType.StringType })
        ) { backStackEntry ->
            val pass_id = backStackEntry.arguments?.getString("pass_id") ?: return@composable
            UpdatePassScreen(navController, pass_id)
        }
        composable(route = Screen.NotificationScreen.route) {
            NotificationScreen(navController)
        }
        composable(route = Screen.RequestScreen.route) {
            RequestScreen(navController)
        }
        composable(route = Screen.PassAdminScreen.route) {
            PassAdminScreen(navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(route = Screen.AccessHistoryScreen.route) {
            AccessHistoryScreen(navController)
        }
        composable(route = Screen.CreateUserScreen.route) {
            CreateUserScreen(navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.UserAdminScreen.route)  {
            UserAdminScreen(navController)
        }
        composable(
            route = Screen.UserDetailScreen.route,
            arguments = listOf(navArgument("user_id") { type = NavType.StringType })
        ) { backStackEntry ->
            val user_id = backStackEntry.arguments?.getString("user_id") ?: return@composable
            UserScreen(navController, user_id)
        }
        composable(
            route = Screen.UpdateUserScreen.route,
            arguments = listOf(navArgument("user_id") { type = NavType.StringType })
        ) { backStackEntry ->
            val user_id = backStackEntry.arguments?.getString("user_id") ?: return@composable
            UpdateUserScreen(navController, user_id)
        }
    }
}
