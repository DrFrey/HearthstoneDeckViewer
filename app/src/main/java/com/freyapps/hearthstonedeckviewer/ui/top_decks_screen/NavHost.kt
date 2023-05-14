package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            DeathknightScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Demonhunter.name) {
            DemonhunterScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)

            }
        }
        composable(ScreensRoute.Druid.name) {
            DruidScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Hunter.name) {
            HunterScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Mage.name) {
            MageScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Paladin.name) {
            PaladinScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Priest.name) {
            PriestScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Rogue.name) {
            RogueScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Shaman.name) {
            DeathknightScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Warlock.name) {
            WarlockScreen(viewModel) {
                onDeckNameClick(it, viewModel, navController)
            }
        }
        composable(ScreensRoute.Warrior.name) {
            WarriorScreen(viewModel) {
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
