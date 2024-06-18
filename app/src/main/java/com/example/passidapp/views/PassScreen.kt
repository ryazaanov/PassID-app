package com.example.passidapp.views

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.PassIDAppTheme
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.Pass
import com.example.passidapp.viewmodels.PassViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassScreen(navController: NavController, pass_id: String, passViewModel: PassViewModel = viewModel()) {
    val passState by passViewModel.passState.collectAsState()
    val errorState by passViewModel.errorState.collectAsState()


    LaunchedEffect(pass_id) {
        passViewModel.getPass(pass_id)
    }

    PassIDAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Пропуск")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        passState?.let { pass ->
                            item { PassInfo(pass = pass) }
                        }
                    }
                }
                SettingsButtons(pass_id, passViewModel, navController)
            }
        }
    }

    errorState?.let { error ->
        Text("Error: $error", color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun SettingsButtons(pass_id: String, passViewModel: PassViewModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить этот пропуск?") },
            confirmButton = {
                Button(
                    onClick = {
                        passViewModel.deletePass(pass_id)
                        showDialog = false
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.HomeScreen.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
    ) {
        Button(
            onClick = { navController.navigate(Screen.UpdatePassScreen.createRoute(pass_id)) },
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Text("Обновить пропуск")
        }
        OutlinedButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(horizontal = 2.dp),
        ) {
            Text("Удалить пропуск")
        }
    }
}

@Composable
fun PassInfo(pass: Pass) {
    val formattedIssueDate = formatDate(pass.issue_date)
    val formattedExpiryDate = formatDate(pass.expiry_date)
    val qrCodeBitmap = generateQRCode(pass.pass_id, 1000, 1000)

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(bitmap = qrCodeBitmap.asImageBitmap(), contentDescription = "QR code")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = pass.pass_type, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = pass.pass_name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Дата выпуска: $formattedIssueDate", fontWeight = FontWeight.Normal, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Дата истечения: $formattedExpiryDate", fontWeight = FontWeight.Normal, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}

fun formatDate(dateString: String): String {
    val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
    val date = LocalDateTime.parse(dateString, isoFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return date.format(outputFormatter)
}

fun generateQRCode(content: String, width: Int, height: Int): Bitmap {
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bitmap
}