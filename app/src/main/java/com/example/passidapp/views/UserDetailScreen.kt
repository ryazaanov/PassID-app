package com.example.passidapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.User
import com.example.passidapp.viewmodels.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavController, user_id: String, userViewModel: UserViewModel = viewModel()) {
    val userState by userViewModel.userState.collectAsState()
    val errorState by userViewModel.errorState.collectAsState()

    LaunchedEffect(user_id) {
        userViewModel.getUser(user_id)
    }

    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Информация о пользователе")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        userState?.let { user ->
                            item { UserInfo(user = user) }
                        }
                    }
                }
                SettingsButtons(user_id, userViewModel, navController)
            }
        }
    }

    errorState?.let { error ->
        Text("Error: $error", color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun SettingsButtons(user_id: String, userViewModel: UserViewModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить этого пользователя?") },
            confirmButton = {
                Button(
                    onClick = {
                        userViewModel.deleteUser(user_id)
                        showDialog = false
                        navController.navigate(Screen.UserAdminScreen.route) {
                            popUpTo(Screen.UserAdminScreen.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
    ) {
        Button(
            onClick = { navController.navigate(Screen.UpdateUserScreen.createRoute(user_id)) },
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Text("Обновить")
        }
        OutlinedButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(horizontal = 2.dp),
        ) {
            Text("Удалить")
        }
    }
}

@Composable
fun UserInfo(user: User) {
    val formattedIssueDate = formatDate4(user.birth_date)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${user.first_name} ${user.last_name}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        user.middle_name?.let {
            Text(text = it, fontWeight = FontWeight.Medium, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(text = "Дата рождения: $formattedIssueDate", fontWeight = FontWeight.Normal, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "Контакт: ${user.email_or_phone}", fontWeight = FontWeight.Normal, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(26.dp))
    }
}

fun formatDate4(dateString: String): String {
    val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
    val date = LocalDateTime.parse(dateString, isoFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return date.format(outputFormatter)
}