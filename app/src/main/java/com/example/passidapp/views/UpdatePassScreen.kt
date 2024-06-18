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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.passidapp.models.Pass
import com.example.passidapp.viewmodels.PassViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

data class Zone(val zone_id: String, val zone_name: String, val description: String)
data class User_(val FIO: String, val user_id: String)

fun showDatePicker(context: Context, onDateSelected: (LocalDateTime) -> Unit) {
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
fun UpdatePassScreen(navController: NavController, pass_id: String, passViewModel: PassViewModel = viewModel()) {
    var selectedUser by remember { mutableStateOf("") }
    var user_id by remember { mutableStateOf("") }
    var selectedZone by remember { mutableStateOf<Zone?>(null) }
    var pass_type by remember { mutableStateOf("") }
    var pass_name by remember { mutableStateOf("") }
    var access_level by remember { mutableStateOf("") }
    var issue_date by remember { mutableStateOf<LocalDateTime?>(null) }
    var expiry_date by remember { mutableStateOf<LocalDateTime?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val passState by passViewModel.passState.collectAsState()
    val errorState by passViewModel.errorState.collectAsState()

    val context = LocalContext.current

    val zones = listOf(
        Zone("3fa85f64-5717-4562-b3fc-2c963f66afa6", "Главный Вход", "Основной вход в здание"),
        Zone("2c4b2fb2-5e2d-4a73-b7b7-5d848b4f7083", "Парковка", "Парковочная зона для сотрудников и посетителей"),
        Zone("d704f4d4-8123-4cd4-a2b3-5d0eb6b5e6b6", "Конференц-зал", "Зал для проведения встреч и конференций"),
        Zone("ff37f4c7-4c5f-41d3-a5d2-2d41d8c6f809", "Офисное помещение", "Рабочие офисы для сотрудников компании"),
        Zone("ab5d72d4-76d6-47e6-9a73-55f7c6b7e2a3", "Склад", "Складское помещение для хранения товаров и материалов")
    )

    val passTypes = listOf("Временный пропуск", "Постоянный пропуск")

    val users = listOf(
        User_("Рязанов Д.А.", "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    )

    LaunchedEffect(pass_id) {
        passViewModel.getPass(pass_id)
        passState?.let { pass ->
            selectedUser = users.find { it.user_id == pass.user_id }?.FIO ?: ""
            user_id = pass.user_id
            selectedZone = zones.find { it.zone_id == pass.zone_id }
            pass_type = pass.pass_type
            pass_name = pass.pass_name
            access_level = pass.access_level.toString()
            issue_date = LocalDateTime.parse(pass.issue_date, dateFormatter)
            expiry_date = LocalDateTime.parse(pass.expiry_date, dateFormatter)
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
            Text(text = "Обновление пропуска")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            // Выпадающее меню для User ID
            var userExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = userExpanded,
                onExpandedChange = { userExpanded = !userExpanded }
            ) {
                TextField(
                    value = selectedUser,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("User") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = userExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = userExpanded,
                    onDismissRequest = { userExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    users.forEach { user_ ->
                        DropdownMenuItem(
                            onClick = {
                                selectedUser = user_.FIO
                                user_id = user_.user_id
                                userExpanded = false
                            },
                            text = { Text(user_.FIO) }
                        )
                    }
                }
            }

            var zoneExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = zoneExpanded,
                onExpandedChange = { zoneExpanded = !zoneExpanded }
            ) {
                TextField(
                    value = selectedZone?.zone_name ?: "",
                    onValueChange = {},
                    readOnly = false,
                    label = { Text("Zone") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = zoneExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = zoneExpanded,
                    onDismissRequest = { zoneExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    zones.forEach { zone ->
                        DropdownMenuItem(
                            onClick = {
                                selectedZone = zone
                                zoneExpanded = false
                            },
                            text = { Text(zone.zone_name) }
                        )
                    }
                }
            }

            var typeExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = !typeExpanded }
            ) {
                TextField(
                    value = pass_type,
                    onValueChange = {},
                    readOnly = false,
                    label = { Text("Тип пропуска") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = { typeExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    passTypes.forEach { type ->
                        DropdownMenuItem(
                            onClick = {
                                pass_type = type
                                typeExpanded = false
                            },
                            text = { Text(type) }
                        )
                    }
                }
            }

            TextField(
                value = pass_name,
                onValueChange = { pass_name = it },
                label = { Text("Pass Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            if (pass_type == "Временный пропуск") {
                Button(
                    onClick = {
                        showDatePicker(context) { selectedDate ->
                            issue_date = selectedDate.withHour(9).withMinute(0).withSecond(0).withNano(0)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(issue_date?.format(dateFormatter) ?: "Выберите дату выпуска")
                }
                Button(
                    onClick = {
                        showDatePicker(context) { selectedDate ->
                            expiry_date = selectedDate.withHour(18).withMinute(0).withSecond(0).withNano(0)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(expiry_date?.format(dateFormatter) ?: "Выберите дату окончания")
                }
            }

            TextField(
                value = access_level,
                onValueChange = { access_level = it },
                label = { Text("Уровень доступа (0-4)") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Отправить заявку на обновление")
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
            text = { Text("Вы уверены, что хотите обновить пропуск?") },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedZone != null) {
                            val updatedPass = Pass(
                                pass_id = pass_id,
                                user_id = user_id,
                                zone_id = selectedZone!!.zone_id,
                                pass_name = pass_name,
                                pass_type = pass_type,
                                issue_date = issue_date?.format(dateFormatter)
                                    ?: "2024-06-10T09:00:00.000000",
                                expiry_date = expiry_date?.format(dateFormatter)
                                    ?: "2025-06-10T18:00:00.000000",
                                access_level = access_level.toInt()
                            )
                            passViewModel.updatePass(pass_id, updatedPass)
                            navController.navigate("home_screen") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
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
