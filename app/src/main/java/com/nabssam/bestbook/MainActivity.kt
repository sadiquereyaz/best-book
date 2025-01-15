package com.nabssam.bestbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.presentation.BestBookApp
import com.nabssam.bestbook.presentation.theme.BestBookTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var authManager: AuthManager
    @Inject lateinit var connectivityObserver: NetworkConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BestBookTheme {
                Surface(tonalElevation = 5.dp) {
                    BestBookApp(
                        modifier = Modifier.fillMaxSize(),
                        authManager = authManager,
                        connectivityObserver = connectivityObserver
                    )
                }
            }
        }
    }
}

