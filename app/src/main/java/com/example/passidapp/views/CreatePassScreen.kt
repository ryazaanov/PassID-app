package com.example.passidapp.views

import androidx.benchmark.perfetto.ExperimentalPerfettoTraceProcessorApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.PassCreateModel
import com.example.passidapp.viewmodels.CreatePassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePassScreen(
    navController: NavController,
    //viewModel: CreatePassViewModel = viewModel
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Создание пропуска")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screen.PassTypeScreen.route) }) {
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
                        item{ PassCreate() }

                    }

                }
                //CreateButton()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPerfettoTraceProcessorApi::class)
@Composable
fun PassCreate() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Имя") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Фамилия") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Место") },
            leadingIcon = { Icon(Icons.Filled.Search,
                contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(2.dp))

    }

}

@Composable
fun CreateButton(viewModel: CreatePassViewModel, firstName: String, lastName: String, place: String) {
    Button(
        onClick = {
            val passCreateModel = PassCreateModel(firstName, lastName, place)
            viewModel.createPass(passCreateModel)
        },
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Text("Создать пропуск")
    }
}


