package com.example.passidapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationInfoScreen(navController: NavController) {
    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Уведомление")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screen.NotificationScreen.route) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
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
                        item { News(notType = "Важное", notTitle = "Пропуск закончится 31 декабря", notText = "Какой-то пример текста") }

                    }
                }
            }
        }
    }
}

@Composable
fun News(notType: String, notTitle: String, notText: String){
    Box() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            //horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Фото
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                // Circle with photo
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = "Image icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(56.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Тип уведомления
                Text(text = notType, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(2.dp))
                // Заголовок уведомления
                Text(text = notTitle, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(4.dp))
                // Текст уведомления
                Text(text = notText, fontWeight = FontWeight.Normal, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(26.dp))
            }

        }
    }
}

