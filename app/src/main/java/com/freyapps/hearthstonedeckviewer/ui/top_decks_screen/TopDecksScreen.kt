package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.freyapps.hearthstonedeckviewer.ui.MainViewModel
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.ui.NavHost
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopDecksScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {

    val error = viewModel.error
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                titleResId = R.string.app_name,
                openDrawer =
                {
                    scope.launch {
                        // Open the drawer with animation
                        // and suspend until it is fully
                        // opened or animation has been canceled
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = true,
        drawerContent = {
            DrawerBody(
                menuItems = menuItems,
                scaffoldState,
                scope
            ) {
                navController.navigate(it.id.name) {
                    popUpTo = navController.graph.startDestinationId
                    launchSingleTop = true
                }
            }
        }
    ) {
        NavHost(navController = navController, viewModel = viewModel, onClick = onClick)
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotEmpty()) {
            when (scaffoldState.snackbarHostState.showSnackbar(error)) {
                SnackbarResult.Dismissed -> viewModel.clearErrorMessage()
                else -> {}
            }
        }
    }
}
