package com.freyapps.hearthstonedeckviewer.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.freyapps.hearthstonedeckviewer.data.models.remote.Duels

@Entity
data class CardLocal(
    val armor: Int,
    val artistName: String,
    val attack: Int,
    val cardSetId: Int,
    val cardTypeId: Int,
    val childIds: List<Int>,
    val classId: Int,
    val collectible: Int,
    val cropImage: String,
    val duels: Duels?,
    val durability: Int,
    val flavorText: String,
    val health: Int,
    val id: Int,
    val image: String,
    val imageGold: String,
    val keywordIds: List<Int>,
    val manaCost: Int,
    val minionTypeId: Int,
    val multiClassIds: List<Int>,
    val name: String,
    val rarityId: Int,
    @PrimaryKey val slug: String,
    val spellSchoolId: Int,
    val text: String
)