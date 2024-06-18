package com.example.passidapp.views

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.passidapp.models.User
import com.example.passidapp.viewmodels.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

fun showDatePicker3(context: Context, onDateSelected: (LocalDateTime) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateUserScreen(navController: NavController, user_id: String, userViewModel: UserViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf<LocalDateTime?>(null) }
    var emailOrPhone by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val userState by userViewModel.userState.collectAsState()
    val errorState by userViewModel.errorState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(user_id) {
        userViewModel.getUser(user_id)
        userState?.let { user ->
            firstName = user.first_name
            lastName = user.last_name
            middleName = user.middle_name ?: ""
            birthDate = LocalDateTime.parse(user.birth_date, dateFormatter)
            emailOrPhone = user.email_or_phone
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
            }
            Text(text = "Обновление пользователя")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Имя") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Фамилия") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = middleName,
                onValueChange = { middleName = it },
                label = { Text("Отчество") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Button(
                onClick = {
                    showDatePicker(context) { selectedDate ->
                        birthDate = selectedDate.withHour(0).withMinute(0).withSecond(0).withNano(0)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(birthDate?.format(dateFormatter) ?: "Выберите дату рождения")
            }
            TextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                label = { Text("Email или телефон") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Обновить пользователя")
            }
            Spacer(modifier = Modifier.height(16.dp))

            errorState?.let { error ->
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Подтверждение") },
            text = { Text("Вы уверены, что хотите обновить информацию пользователя?") },
            confirmButton = {
                Button(
                    onClick = {
                        val updatedUser = User(
                            user_id = user_id,
                            first_name = firstName,
                            last_name = lastName,
                            middle_name = middleName,
                            birth_date = birthDate?.format(dateFormatter) ?: "2000-01-01T00:00:00.000000",
                            email_or_phone = emailOrPhone,
                            password = "1",
                            admins = listOf(),
                            passes = listOf(),
                            access_logs = listOf(),
                            pass_requests = listOf()

                        )
                        userViewModel.updateUser(user_id, updatedUser)
                        navController.navigate("home_screen") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Нет")
                }
            }
        )
    }
}
