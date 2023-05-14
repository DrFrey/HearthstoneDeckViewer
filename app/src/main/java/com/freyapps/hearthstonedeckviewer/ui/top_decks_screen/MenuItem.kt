package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass

sealed class MenuItem(var id: ScreensRoute, var title: Int, var color: Int, var hearthstoneClass: HearthstoneClass) {
    object DeathKnight : MenuItem(ScreensRoute.DeathKnight, R.string.deathknight, R.color.deathknight_grey, HearthstoneClass.DEATHKNIGHT)
    object Demonhunter : MenuItem(ScreensRoute.Demonhunter, R.string.demonhunter, R.color.demonhunter_green, HearthstoneClass.DEMONHUNTER)
    object Druid : MenuItem(ScreensRoute.Druid, R.string.druid, R.color.druid_brown, HearthstoneClass.DRUID)
    object Hunter : MenuItem(ScreensRoute.Hunter, R.string.hunter, R.color.hunter_green, HearthstoneClass.HUNTER)
    object Mage : MenuItem(ScreensRoute.Mage, R.string.mage, R.color.mage_sky_blue, HearthstoneClass.MAGE)
    object Paladin : MenuItem(ScreensRoute.Paladin, R.string.paladin, R.color.paladin_yellow, HearthstoneClass.PALADIN)
    object Priest : MenuItem(ScreensRoute.Priest, R.string.priest, R.color.priest_silver, HearthstoneClass.PRIEST)
    object Rogue : MenuItem(ScreensRoute.Rogue, R.string.rogue, R.color.rogue_grey, HearthstoneClass.ROGUE)
    object Shaman : MenuItem(ScreensRoute.Shaman, R.string.shaman, R.color.shaman_blue, HearthstoneClass.SHAMAN)
    object Warlock : MenuItem(ScreensRoute.Warlock, R.string.warlock, R.color.warlock_purple, HearthstoneClass.WARLOCK)
    object Warrior : MenuItem(ScreensRoute.Warrior, R.string.warrior, R.color.warrior_tan, HearthstoneClass.WARRIOR)
}

val menuItems = listOf(
    MenuItem.DeathKnight,
    MenuItem.Demonhunter,
    MenuItem.Druid,
    MenuItem.Hunter,
    MenuItem.Mage,
    MenuItem.Paladin,
    MenuItem.Priest,
    MenuItem.Rogue,
    MenuItem.Shaman,
    MenuItem.Warlock,
    MenuItem.Warrior,
)

enum class ScreensRoute {
    DeathKnight, Demonhunter, Druid, Hunter, Mage, Paladin, Priest, Rogue, Shaman, Warlock, Warrior,
    DeckList
}