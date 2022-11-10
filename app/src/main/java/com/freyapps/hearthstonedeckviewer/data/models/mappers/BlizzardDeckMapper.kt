package com.freyapps.hearthstonedeckviewer.data.models.mappers

import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import com.freyapps.hearthstonedeckviewer.data.models.remote.Deck
import javax.inject.Inject

class BlizzardDeckMapper @Inject constructor() {

    fun mapBlizzardDeckToLocal(deck: Deck): DeckLocal {
        with(deck) {
            val cardsWithQty: MutableMap<Card, Int> = mutableMapOf()
            if (!cards.isNullOrEmpty()) {
                cards.forEach { card ->
                    if (card !in cardsWithQty) {
                        cardsWithQty[card] = 1
                    } else {
                        cardsWithQty[card] = 2
                    }
                }
            }

            return DeckLocal(
                name = "${classX?.name}-$format-$version",
                cardCount = cardCount ?: 0,
                cards = cardsWithQty.toMap(),
                classX = classX,
                code = deckCode ?: "",
                format = format ?: "",
                hero = hero,
                heroPower = heroPower,
                version = version ?: 1
            )
        }
    }
}