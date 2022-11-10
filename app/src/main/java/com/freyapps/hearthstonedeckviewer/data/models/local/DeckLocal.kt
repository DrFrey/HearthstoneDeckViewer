package com.freyapps.hearthstonedeckviewer.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import com.freyapps.hearthstonedeckviewer.data.models.remote.ClassX
import com.freyapps.hearthstonedeckviewer.data.models.remote.Hero
import com.freyapps.hearthstonedeckviewer.data.models.remote.HeroPower

@Entity
data class DeckLocal(
    val name: String,
    val cardCount: Int,
    val cards: Map<Card, Int>,
    val classX: ClassX?,
    @PrimaryKey val code: String,
    val format: String,
    val hero: Hero?,
    val heroPower: HeroPower?,
    val version: Int
)
