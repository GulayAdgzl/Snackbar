package com.glyadgzl.snackbar


import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SnackbarDemo()
                }
            }
        }
    }
}

@Composable
fun SnackbarDemo() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Çevrimdışı durumu simüle etmek için
    var isOffline by remember { mutableStateOf(false) }

    // Çevrimdışı durum değiştiğinde Snackbar göster
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = "İnternet bağlantısı yok!",
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {

        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Normal Snackbar butonu
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Bu bir bildirim mesajıdır",
                                actionLabel = "Tamam",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Normal Snackbar Göster")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Uzun Snackbar butonu
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Bu uzun süre görüntülenen bir mesajdır",
                                actionLabel = "Kapat",
                                duration = SnackbarDuration.Long
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Uzun Snackbar Göster")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Çevrimdışı durumu simüle eden buton
                Button(
                    onClick = { isOffline = !isOffline },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isOffline) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isOffline) "Çevrimiçi Ol" else "Çevrimdışı Ol")
                }
            }
        }
    )
}