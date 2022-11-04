package com.freyapps.hearthstonedeckviewer.data.models.mappers

import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.remote.Deck
import javax.inject.Inject

class BlizzardDeckMapper @Inject constructor() {

    fun mapBlizzardDeckToLocal(deck: Deck): DeckLocal =
        with(deck) {
            DeckLocal(
                name = "${classX?.name}-$format-$version",
                cardCount = cardCount ?: 0,
                cards = cards ?: listOf(),
                classX = classX,
                code = deckCode ?: "",
                format = format ?: "",
                hero = hero,
                heroPower = heroPower,
                version = version ?: 1
            )
        }

}