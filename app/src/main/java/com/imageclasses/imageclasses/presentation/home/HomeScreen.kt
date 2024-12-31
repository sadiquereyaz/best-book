package com.imageclasses.imageclasses.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.presentation.addnote.EventAddNoteScreen
import com.imageclasses.imageclasses.presentation.addnote.StateAddNoteScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
){

}
