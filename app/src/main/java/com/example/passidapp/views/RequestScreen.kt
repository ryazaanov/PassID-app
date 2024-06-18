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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.BottomNavigationAdmin
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.viewmodels.PassViewModel

data class Request(val type: String, val title: String, val date: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(navController: NavController, passViewModel: PassViewModel = viewModel()) {
    val passesState = passViewModel.passesState.collectAsState().value
    val errorState = passViewModel.errorState.collectAsState().value
    passViewModel.getPasses()

    // Local map to track confirmation status
    val confirmationStatus = remember { mutableStateMapOf<String, Boolean>() }

    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Заявки")
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Выйти")
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    when {
                        passesState.isEmpty() && errorState == null -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        errorState != null -> {
                            Text(
                                text = "Ошибка: $errorState",
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        passesState.isNotEmpty() -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(passesState) { pass ->
                                    val isConfirmed = confirmationStatus[pass.pass_id] ?: false
                                    RequestCard(
                                        Request(
                                            type = pass.pass_type,
                                            title = pass.pass_name,
                                            date = "10.06.2024" // Placeholder date, replace as needed
                                        ),
                                        isConfirmed = isConfirmed,
                                        onConfirm = { confirmationStatus[pass.pass_id] = true },
                                        onReject = { confirmationStatus[pass.pass_id] = false }
                                    )
                                }
                            }
                        }
                    }
                }
                BottomNavigationAdmin(selectedItemIndex = 0, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestCard(request: Request, isConfirmed: Boolean, onConfirm: () -> Unit, onReject: () -> Unit) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }

    if (showConfirmDialog) {
        ConfirmationDialog(
            title = "Подтверждение",
            text = "Вы уверены, что хотите подтвердить заявку?",
            onConfirm = {
                onConfirm()
                showConfirmDialog = false
            },
            onDismiss = { showConfirmDialog = false }
        )
    }

    if (showRejectDialog) {
        ConfirmationDialog(
            title = "Отклонение",
            text = "Вы уверены, что хотите отклонить заявку?",
            onConfirm = {
                onReject()
                showRejectDialog = false
            },
            onDismiss = { showRejectDialog = false }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Тип заявки
            Text(text = request.type, fontWeight = FontWeight.Medium, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            // Текст заявки
            Text(text = request.title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            // Дата заявки
            Text(text = request.date, fontWeight = FontWeight.Light, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            // Статус заявки
            Text(
                text = if (isConfirmed) "Заявка подтверждена" else "Заявка не подтверждена!",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = if (isConfirmed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Иконки для принятия или отклонения заявки
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { showConfirmDialog = true }) {
                    Icon(Icons.Filled.Done, contentDescription = "Принять заявку")
                }
                IconButton(onClick = { showRejectDialog = true }) {
                    Icon(Icons.Filled.Close, contentDescription = "Отклонить заявку")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ConfirmationDialog(title: String, text: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RequestScreenPreview() {
    val navController = rememberNavController()
    RequestScreen(navController = navController)
}
