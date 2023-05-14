package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.freyapps.hearthstonedeckviewer.ui.MainViewModel
import com.freyapps.hearthstonedeckviewer.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopDecksScreen(
    viewModel: MainViewModel
) {

    val error = viewModel.error
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var actionBarTitle by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = actionBarTitle,
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
        NavHost(navController = navController, viewModel = viewModel)
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotEmpty()) {
            when (scaffoldState.snackbarHostState.showSnackbar(error)) {
                SnackbarResult.Dismissed -> viewModel.clearErrorMessage()
                else -> {}
            }
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            // You can map the title based on the route using:
            actionBarTitle = getClassByRoute(context, backStackEntry.destination.route ?: "")
        }
    }
}

private fun getClassByRoute(context: Context, route: String): String {
    return  when(route) {
        ScreensRoute.DeathKnight.name -> context.getString(R.string.deathknight)
        ScreensRoute.Demonhunter.name -> context.getString(R.string.demonhunter)
        ScreensRoute.Druid.name -> context.getString(R.string.druid)
        ScreensRoute.Hunter.name -> context.getString(R.string.hunter)
        ScreensRoute.Mage.name -> context.getString(R.string.mage)
        ScreensRoute.Paladin.name -> context.getString(R.string.paladin)
        ScreensRoute.Priest.name -> context.getString(R.string.priest)
        ScreensRoute.Rogue.name -> context.getString(R.string.rogue)
        ScreensRoute.Shaman.name -> context.getString(R.string.shaman)
        ScreensRoute.Warlock.name -> context.getString(R.string.warlock)
        ScreensRoute.Warrior.name -> context.getString(R.string.warrior)
        else -> ""
    }
}