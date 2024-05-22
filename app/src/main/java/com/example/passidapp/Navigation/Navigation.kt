package com.example.passidapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passidapp.views.CreatePassScreen
import com.example.passidapp.views.HomeScreen
import com.example.passidapp.views.NotificationInfoScreen
import com.example.passidapp.views.NotificationScreen
import com.example.passidapp.views.PassScreen
import com.example.passidapp.views.PassTypeScreen
import com.example.passidapp.views.ProfileScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = Screen.PassScreen.route){
            PassScreen(navController)
        }
        composable(route = Screen.PassTypeScreen.route){
            PassTypeScreen(navController)
        }
        composable(route = Screen.CreatePassScreen.route){
            CreatePassScreen(navController)
        }
        composable(route = Screen.NotificationScreen.route){
            NotificationScreen(navController)
        }
        composable(route = Screen.NotificationInfoScreen.route){
            NotificationInfoScreen(navController)
        }
        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController)
        }
    }
}