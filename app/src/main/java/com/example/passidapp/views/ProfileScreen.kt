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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.BottomNavigation


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
                ){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)

                    )
                    {
                        item{ Profile(name = "Имя", surname = "Фамилия") }
                        item{ ThreeButtons() }
                    }
                }
                BottomNavigation(selectedItemIndex = 2, navController=navController)
            }
        }
    }
}

@Composable
fun Profile(name: String, surname: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                // Circle with photo
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Person icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

            // Name and surname
            Column {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = surname, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ThreeButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Button 1
            Button(
                onClick = { /* Handle button 1 click */ },
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Icon(
                    /*modifier = Modifier
                        .align(Alignment.Start),*/
                    imageVector = Icons.Filled.AttachFile,
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    contentDescription = "Документы"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Документы")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Button 2
            Button(
                onClick = { /* Handle button 2 click */ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    contentDescription = "Настройки"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Настройки")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Button 3
            Button(
                onClick = { /* Handle button 3 click */ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    contentDescription = "Выход из приложения"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Выход из приложения")
            }
        }
    }
}
