package com.example.passidapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.BottomNavigationAdmin
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.User
import com.example.passidapp.viewmodels.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(navController: NavController, user: User) {
    val formattedIssueDate = formatDate5(user.birth_date)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = {
            navController.navigate(Screen.UserDetailScreen.createRoute(user.user_id))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Имя пользователя
            Text(text = "${user.first_name} ${user.last_name}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            // Отчество пользователя
            user.middle_name?.let {
                Text(text = it, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Дата рождения пользователя
            Text(text = "Дата рождения: $formattedIssueDate", fontWeight = FontWeight.Light, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            // Email или телефон
            Text(text = "Контакт: ${user.email_or_phone}", fontWeight = FontWeight.Light, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun NewUser(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(Screen.CreateUserScreen.route) },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAdminScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val usersState = userViewModel.usersState.collectAsState().value
    val errorState = userViewModel.errorState.collectAsState().value
    userViewModel.getUsers()

    PassIDAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                TopAppBar(
                    title = { Text(text = "Пользователи") },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Scan QR code")
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    when {
                        usersState.isEmpty() && errorState == null -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        errorState != null -> {
                            Text(
                                text = "Ошибка: $errorState",
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        usersState.isNotEmpty() -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(usersState) { user ->
                                    UserCard(navController, user)
                                }
                            }
                        }
                    }
                    NewUser(navController)
                }
                BottomNavigationAdmin(selectedItemIndex = 2, navController = navController)
            }
        }
    }
}

fun formatDate5(dateString: String): String {
    val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
    val date = LocalDateTime.parse(dateString, isoFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return date.format(outputFormatter)
}