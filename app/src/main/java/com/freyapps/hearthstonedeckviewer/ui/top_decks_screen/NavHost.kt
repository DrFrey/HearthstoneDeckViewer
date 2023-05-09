package com.freyapps.hearthstonedeckviewer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.ui.top_decks_screen.*

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.DeathKnight.name
    ) {
        composable(ScreensRoute.DeathKnight.name) {
            DeathknightScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Demonhunter.name) {
            DemonhunterScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Druid.name) {
            DruidScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Hunter.name) {
            HunterScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Mage.name) {
            MageScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Paladin.name) {
            PaladinScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Priest.name) {
            PriestScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Rogue.name) {
            RogueScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Shaman.name) {
            DeathknightScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Warlock.name) {
            WarlockScreen(viewModel, onClick)
        }
        composable(ScreensRoute.Warrior.name) {
            WarriorScreen(viewModel, onClick)
        }
    }
}