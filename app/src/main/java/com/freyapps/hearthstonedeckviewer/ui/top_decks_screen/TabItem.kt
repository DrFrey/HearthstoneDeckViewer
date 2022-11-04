package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass.*

sealed class TabItem(var title: Int, var color: Int, var hearthstoneClass: HearthstoneClass) {
    object Hunter : TabItem(R.string.hunter, R.color.hunter_green, HUNTER)
    object Mage : TabItem(R.string.mage, R.color.mage_sky_blue, MAGE)
    object Warrior : TabItem(R.string.warrior, R.color.warrior_tan, WARRIOR)
    object Druid : TabItem(R.string.druid, R.color.druid_brown, DRUID)
    object Rogue : TabItem(R.string.rogue, R.color.rogue_grey, ROGUE)
    object Shaman : TabItem(R.string.shaman, R.color.shaman_blue, SHAMAN)
    object Demonhunter : TabItem(R.string.demonhunter, R.color.demonhunter_green, DEMONHUNTER)
    object Priest : TabItem(R.string.priest, R.color.priest_silver, PRIEST)
    object Paladin : TabItem(R.string.paladin, R.color.paladin_yellow, PALADIN)
    object Warlock : TabItem(R.string.warlock, R.color.warlock_purple, WARLOCK)
}
