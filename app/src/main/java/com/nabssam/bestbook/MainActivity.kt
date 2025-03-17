package com.nabssam.bestbook

import android.graphics.Color.toArgb
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.presentation.BestBookApp
import com.nabssam.bestbook.presentation.theme.BestBookTheme
import com.nabssam.bestbook.presentation.theme.backgroundLight
import com.nabssam.bestbook.presentation.theme.onSurfaceLight
import com.nabssam.bestbook.presentation.theme.scrimLight
import com.nabssam.bestbook.presentation.theme.surfaceContainerLight
import com.nabssam.bestbook.presentation.theme.surfaceLight
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authManager: AuthManager
    @Inject
    lateinit var snackbarManager: SnackbarManager
    @Inject
    lateinit var connectivityObserver: NetworkConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navigationContainer =
//            getColor(R.color.navigation_container)
        surfaceLight.toArgb()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = navigationContainer,
                darkScrim = navigationContainer
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = navigationContainer,
                darkScrim = navigationContainer
            )
        )
        setContent {
            BestBookTheme {
                Surface(/*tonalElevation = 5.dp*/) {
                    BestBookApp(
                        modifier = Modifier.fillMaxSize(),
                        authManager = authManager,
                        snackbarManager = snackbarManager,
                        connectivityObserver = connectivityObserver
                    )
                }
            }
        }
    }

}