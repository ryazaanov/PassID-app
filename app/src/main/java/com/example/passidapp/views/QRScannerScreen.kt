package com.example.passidapp.views

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.passidapp.Navigation.Screen
import com.example.passidapp.models.Pass
import com.example.passidapp.viewmodels.PassViewModel

@Composable
fun QRScannerScreen(navController: NavController, passViewModel: PassViewModel = viewModel()) {
    val context = LocalContext.current as Activity
    var scanResult by remember { mutableStateOf("") }
    val passState by passViewModel.passState.collectAsState()
    val errorState by passViewModel.errorState.collectAsState()

    val qrScannerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            scanResult = result.data?.getStringExtra("QR_SCAN_RESULT") ?: "No result"
            passViewModel.getPass(scanResult)
        } else {
            scanResult = "Scan cancelled"
        }
    }

    LaunchedEffect(Unit) {
        qrScannerLauncher.launch(Intent(context, QRScannerActivity::class.java))
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            passState != null -> {
                PassDetailsScreen(navController, passState!!)
            }
            errorState != null -> {
                Text("Error: $errorState", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun PassDetailsScreen(navController: NavController, pass: Pass) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pass Name: ${pass.pass_name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Pass Type: ${pass.pass_type}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "User ID: ${pass.user_id}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Zone ID: ${pass.zone_id}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Issue Date: ${pass.issue_date}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Expiry Date: ${pass.expiry_date}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Access Level: ${pass.access_level}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
            Text("Back to Home")
        }
    }
}
