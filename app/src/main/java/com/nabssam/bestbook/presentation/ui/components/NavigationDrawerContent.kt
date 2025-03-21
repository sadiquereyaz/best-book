package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.presentation.navigation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    authManager: AuthManager
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Best  Book",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { scope.launch { drawerState.close() } }) {
                Icon(Icons.Default.Close, "close drawer")
            }
        }
        HorizontalDivider()

        Text(
            "Section 1",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Item 1") },
            selected = false,
            onClick = { /* Handle click */ }
        )
        NavigationDrawerItem(
            label = { Text("Item 2") },
            selected = false,
            onClick = { /* Handle click */ }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            "Section 2",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )

        NavigationDrawerItem(
            label = { Text("All Orders") },
            selected = false,
            icon = { Icon(imageVector = ImageVector.vectorResource(R.drawable.orders), contentDescription = null, modifier = Modifier.size(24.dp)) },
            badge = { Text("20") }, // Placeholder
            onClick = {
                navController.navigate(Route.AllOrderRoute())
                scope.launch { drawerState.close() }
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            "Section 3",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            badge = { Text("20") }, // Placeholder
            onClick = { /* Handle click */ }
        )

        NavigationDrawerItem(
            label = { Text("Support") },
            selected = false,
            icon = { Icon(imageVector = ImageVector.vectorResource(R.drawable.support), contentDescription = null, modifier = Modifier.size(24.dp)) },
            onClick = { /* Handle click */ }
        )

        NavigationDrawerItem(
            label = { Text("Help and feedback") },
            selected = false,
            icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
            onClick = { /* Handle click */ },
        )

        NavigationDrawerItem(
            label = { Text("Logout") },
            selected = false,
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.log_out),
                    contentDescription = null
                )
            },
            onClick = {
                scope.launch {
                    authManager.logout()
                }
                scope.launch { drawerState.close() }
            }
        )
        Spacer(Modifier.height(12.dp))
    }
}