
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.viewmodels.PassViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

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
fun CreatePassScreen(navController: NavController, passViewModel: PassViewModel = viewModel()) {
    var selectedUser by remember { mutableStateOf("") }
    var user_id by remember { mutableStateOf("") }
    var selectedZone by remember { mutableStateOf<Zone?>(null) }
    var pass_type by remember { mutableStateOf("") }
    var pass_name by remember { mutableStateOf("") }
    var access_level by remember { mutableStateOf("") }
    var issue_date by remember { mutableStateOf<LocalDateTime?>(null) }
    var expiry_date by remember { mutableStateOf<LocalDateTime?>(null) }
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
                        Text(text = "Создание пропуска")
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
                            readOnly = true,
                            label = { Text("Zone") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = zoneExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
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
                            readOnly = true,
                            label = { Text("Тип пропуска") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
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
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    //Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if (selectedZone != null) {
                                    val newPassId = UUID.randomUUID().toString()
                                    passViewModel.createPass(
                                        pass_id = newPassId,
                                        user_id = user_id,
                                        zone_id = selectedZone!!.zone_id,
                                        pass_name = pass_name,
                                        pass_type = pass_type,
                                        issue_date = issue_date?.format(dateFormatter) ?: "2024-06-10T09:00:00.000000",
                                        expiry_date = expiry_date?.format(dateFormatter) ?: "2025-06-10T18:00:00.000000",
                                        access_level = access_level.toInt()
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text("Отправить заявку на пропуск")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    passState?.let { pass ->
                        Text("Pass Created: ${pass.pass_type} for user ${pass.user_id}")
                    }

                    errorState?.let { error ->
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePassScreenPreview() {
    val navController = rememberNavController()
    CreatePassScreen(navController = navController)
}
