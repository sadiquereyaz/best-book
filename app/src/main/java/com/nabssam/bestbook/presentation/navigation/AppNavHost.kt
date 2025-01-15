 package com.nabssam.bestbook.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.data.repository.auth.UserPreferencesTokenStorage
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import com.nabssam.bestbook.presentation.navigation.auth.authGraph
import com.nabssam.bestbook.presentation.navigation.main.mainAppGraph


 @RequiresApi(Build.VERSION_CODES.O)
 @Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    innerPadding: PaddingValues,
) {

    NavHost(
        navController = navController,
        startDestination = AppNavigation().getStartDestination(),
        modifier = modifier.padding(innerPadding),
    ) {

        // registration nested graph
        authGraph(navController)

        // main app nested graph
        mainAppGraph(navController)
    }
}

//Dependency Injection (preferred)
class AppNavigation( ) {
    fun getStartDestination(): Route {
        val isLoggedIn = false  // TODO: check for already logged in here to avoid displaying signin screen
        return if (isLoggedIn) Route.MainGraph else Route.AuthGraph
    }
}
