package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.ui.MainViewModel
import com.freyapps.hearthstonedeckviewer.ui.deck_screen.DeckScreen

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.DeathKnight.name
    ) {
        composable(ScreensRoute.DeathKnight.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.DEATHKNIGHT) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Demonhunter.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.DEMONHUNTER) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Druid.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.DRUID) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Hunter.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.HUNTER) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Mage.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.MAGE) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Paladin.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.PALADIN) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Priest.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.PRIEST) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Rogue.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.ROGUE) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Shaman.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.SHAMAN) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Warlock.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.WARLOCK) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Warrior.name) {
            ClassScreen(
                viewModel = viewModel,
                hearthstoneClass = HearthstoneClass.WARRIOR) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.DeckList.name) {
            DeckScreen(viewModel)
        }
    }
}

private fun onDeckNameClick(
    deckCode: String,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    viewModel.getDeck(deckCode)
    navController.navigate(ScreensRoute.DeckList.name)
}
