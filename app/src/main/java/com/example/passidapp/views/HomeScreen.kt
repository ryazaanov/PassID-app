package com.example.passidapp.views

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import com.example.passidapp.Navigation.BottomNavigation
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.PassCardModel
import com.example.passidapp.viewmodels.PassCardViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassCard(navController: NavController, passCardModel: PassCardModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = {
            navController.navigate(Screen.PassScreen.route)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Тип пропуска
            Text(text = passCardModel.passType, fontWeight = FontWeight.Medium, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            // Название пропуска
            Text(text = passCardModel.companyName, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(26.dp))

            TextButton(
                onClick = {
                    navController.navigate(Screen.PassScreen.route)
                },

                modifier = Modifier
                    .fillMaxWidth()

                    .align(
                        Alignment.CenterHorizontally
                    )

            ) {
                Row() {
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
            onClick = { navController.navigate(Screen.PassTypeScreen.route) },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, passCardViewModel: PassCardViewModel = viewModel()) {
    val uiState = passCardViewModel.uiState.collectAsState().value

    PassIDAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                TopAppBar(title = { Text(text = "Мои пропуска") }, actions = { /* Actions */ })
                Box(modifier = Modifier.fillMaxWidth()
                                        .fillMaxHeight(0.9f)) {
//                    Проверяем состояние загрузки
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else if (uiState.error != null) {
                            Text(
                                text = "Ошибка: ${uiState.error}",
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Используем правильную функцию 'items' для списка
                            items(uiState.passCards) { passCard ->
                                // Убедитесь, что функция PassCard принимает объект PassCardModel
                                PassCard(navController, passCard)
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

