package com.example.passidapp.views

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.BottomNavigation
import com.example.passidapp.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Мой профиль")
                    },
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item { Profile(name = "Данила", surname = "Рязанов", middleName = "Алексеевич", birthDate = "27.06.2002") }
                        item { ThreeButtons(navController = navController) }
                    }
                }
                BottomNavigation(selectedItemIndex = 2, navController = navController)
            }
        }
    }
}

@Composable
fun Profile(name: String, surname: String, middleName: String?, birthDate: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Person icon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

            Column {
                Text(text = surname, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(text = name, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                if (!middleName.isNullOrEmpty()) {
                    Text(text = middleName, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                }
                Text(text = "Дата рождения: $birthDate", fontWeight = FontWeight.Medium, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ThreeButtons(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SimpleButton(
            icon = Icons.Filled.Edit,
            text = "Редактировать профиль",
            onClick = {  }
        )
        SimpleButton(
            icon = Icons.Filled.History,
            text = "История доступа",
            onClick = { navController.navigate(Screen.AccessHistoryScreen.route) }
        )

        SimpleButton(
            icon = Icons.Filled.AdminPanelSettings,
            text = "Администрирование",
            onClick = { navController.navigate(Screen.RequestScreen.route)}
        )

        SimpleButton(
            icon = Icons.Filled.Settings,
            text = "Настройки",
            onClick = { /* Handle button 4 click */ }
        )

        SimpleButton(
            icon = Icons.Filled.ExitToApp,
            text = "Выход из приложения",
            onClick = { navController.navigate(Screen.LoginScreen.route) }
        )
    }
}

@Composable
fun SimpleButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth().padding(start = 0.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}
