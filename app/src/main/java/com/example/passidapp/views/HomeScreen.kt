package com.example.passidapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.BottomNavigation
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.Pass
import com.example.passidapp.viewmodels.PassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassCard(navController: NavController, pass: Pass, isConfirmed: Boolean) {
    val passId = pass.pass_id
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = {
            navController.navigate(Screen.PassScreen.createRoute(passId))
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
            // Тип пропуска
            Text(text = pass.pass_type, fontWeight = FontWeight.Medium, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            // Название пропуска
            Text(text = pass.pass_name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            // Статус пропуска
            Text(
                text = if (isConfirmed) "Пропуск подтвержден" else "Пропуск не подтвержден!",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = if (isConfirmed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(26.dp))
            TextButton(
                onClick = {
                    navController.navigate(Screen.PassScreen.createRoute(passId))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.QrCode,
                        contentDescription = "QR code"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Открыть QR-код", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun NewPass(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(Screen.CreatePassScreen.route) },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, passViewModel: PassViewModel = viewModel()) {
    val passesState = passViewModel.passesState.collectAsState().value
    val errorState = passViewModel.errorState.collectAsState().value
    passViewModel.getPasses()

    // Local map to track confirmation status
    val confirmationStatus = remember { mutableStateMapOf<String, Boolean>() }

    PassIDAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                TopAppBar(
                    title = { Text(text = "Мои пропуска") },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.QRScannerScreen.route) }) {
                            Icon(Icons.Filled.QrCode, contentDescription = "Scan QR code")
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
                                    PassCard(navController, pass, isConfirmed)
                                }
                            }
                        }
                    }
                    NewPass(navController)
                }
                BottomNavigation(selectedItemIndex = 1, navController = navController)
            }
        }
    }
}
