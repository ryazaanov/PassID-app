package com.example.passidapp.views

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.viewmodels.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

fun showDatePicker1(context: Context, onDateSelected: (LocalDateTime) -> Unit) {
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
fun CreateUserScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    var first_name by remember { mutableStateOf("") }
    var last_name by remember { mutableStateOf("") }
    var middle_name by remember { mutableStateOf("") }
    var birth_date by remember { mutableStateOf<LocalDateTime?>(null) }
    var email_or_phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val userState by userViewModel.userState.collectAsState()
    val errorState by userViewModel.errorState.collectAsState()

    val context = LocalContext.current

    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopAppBar(
                    title = {
                        Text(text = "Создание пользователя")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                    TextField(
                        value = first_name,
                        onValueChange = { first_name = it },
                        label = { Text("Имя") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    TextField(
                        value = last_name,
                        onValueChange = { last_name = it },
                        label = { Text("Фамилия") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    TextField(
                        value = middle_name,
                        onValueChange = { middle_name = it },
                        label = { Text("Отчество") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = {
                            showDatePicker1(context) { selectedDate ->
                                birth_date =
                                    selectedDate.withHour(9).withMinute(0).withSecond(0).withNano(0)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(birth_date?.format(dateFormatter) ?: "Выберите дату рождения")
                    }

                    TextField(
                        value = email_or_phone,
                        onValueChange = { email_or_phone = it },
                        label = { Text("Email или телефон") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Пароль") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            val newUserId = UUID.randomUUID().toString()
                            userViewModel.createUser(
                                user_id = newUserId,
                                first_name = first_name,
                                last_name = last_name,
                                middle_name = middle_name,
                                birth_date = birth_date?.format(dateFormatter)
                                    ?: "2000-01-01T00:00:00.000000",
                                email_or_phone = email_or_phone,
                                password = password,
                                admins = listOf(),
                                passes = listOf(),
                                access_logs = listOf(),
                                pass_requests = listOf()
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Создать пользователя")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    userState?.let { user ->
                        Text("User Created: ${user.first_name} ${user.last_name}")
                    }

                    errorState?.let { error ->
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

        }

    }
}
