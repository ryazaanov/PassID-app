package com.example.passidapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.PassIDAppTheme

data class AccessLog(
    val zoneName: String,
    val entryTime: String,
    val exitTime: String,
    val date: String
)

val zones = listOf(
    Zone("3fa85f64-5717-4562-b3fc-2c963f66afa6", "Главный Вход", "Основной вход в здание"),
    Zone("2c4b2fb2-5e2d-4a73-b7b7-5d848b4f7083", "Парковка", "Парковочная зона для сотрудников и посетителей"),
    Zone("d704f4d4-8123-4cd4-a2b3-5d0eb6b5e6b6", "Конференц-зал", "Зал для проведения встреч и конференций"),
    Zone("ff37f4c7-4c5f-41d3-a5d2-2d41d8c6f809", "Офисное помещение", "Рабочие офисы для сотрудников компании"),
    Zone("ab5d72d4-76d6-47e6-9a73-55f7c6b7e2a3", "Склад", "Складское помещение для хранения товаров и материалов")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessHistoryScreen(navController: NavController) {
    val logs = remember {
        listOf(
            AccessLog("Главный Вход", "08:00", "09:00", "2024-06-10"),
            AccessLog("Парковка", "09:10", "09:30", "2024-06-10"),
            AccessLog("Конференц-зал", "10:00", "12:00", "2024-06-10"),
            AccessLog("Офисное помещение", "12:30", "18:00", "2024-06-10"),
            AccessLog("Склад", "18:10", "18:30", "2024-06-10")
        )
    }

    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "История доступа")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(logs) { log ->
                        AccessLogCard(log = log)
                    }
                }
            }
        }
    }
}

@Composable
fun AccessLogCard(log: AccessLog) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = log.zoneName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Дата: ${log.date}", fontSize = 16.sp)
            Text(text = "Время захода: ${log.entryTime}", fontSize = 16.sp)
            Text(text = "Время ухода: ${log.exitTime}", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccessHistoryScreenPreview() {
    val navController = rememberNavController()
    AccessHistoryScreen(navController = navController)
}
