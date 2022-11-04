package com.freyapps.hearthstonedeckviewer.data.storage

import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import javax.inject.Inject

class DataSourceLocal @Inject constructor(private val db: AppDatabase) {

    fun getTopDecksByClass(hearthstoneClass: HearthstoneClass) =
        db.manacostDeckInfoDao().getDecksByClass(hearthstoneClass)

    suspend fun insertTopDecks(decks: List<ManacostDeckInfo>) =
        db.manacostDeckInfoDao().insertDecks(decks)

    suspend fun insertBlizzardDeck(deck: DeckLocal) =
        db.deckDao().insertDeck(deck)

    suspend fun getDeckById(code: String): DeckLocal? =
        db.deckDao().getDeckById(code)
}