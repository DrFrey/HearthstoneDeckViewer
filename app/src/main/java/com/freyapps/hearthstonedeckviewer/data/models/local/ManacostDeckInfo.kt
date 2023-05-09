package com.freyapps.hearthstonedeckviewer.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ManacostDeckInfo(
    val hearthstoneClass: HearthstoneClass,
    val name: String,
    @PrimaryKey val code: String,
    val likes: Int,
    val dislikes: Int,
    val date: Long
)

enum class HearthstoneClass(val value: String) {
    WARRIOR("voin"),
    DRUID("druid"),
    PRIEST("zhrec"),
    MAGE("mage"),
    HUNTER("hunter"),
    DEMONHUNTER("demonhunter222"),
    PALADIN("paladin"),
    ROGUE("rogue"),
    WARLOCK("warlock"),
    SHAMAN("shaman"),
    DEATHKNIGHT("deathknight"),
    UNKNOWN("unknown")
}
