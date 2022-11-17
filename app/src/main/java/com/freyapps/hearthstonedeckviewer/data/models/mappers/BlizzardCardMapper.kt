package com.freyapps.hearthstonedeckviewer.data.models.mappers

import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import javax.inject.Inject

class BlizzardCardMapper @Inject constructor() {

    fun mapBlizzardCardToLocal(card: Card): CardLocal =
        with(card) {
            CardLocal(
                armor = armor ?: -1,
                artistName = artistName ?: "",
                attack = attack ?: -1,
                cardSetId = cardSetId ?: -1,
                cardTypeId = cardTypeId ?: -1,
                childIds = childIds ?: listOf(),
                classId = classId ?: -1,
                collectible = collectible ?: -1,
                cropImage = cropImage ?: "",
                duels = duels,
                durability = durability ?: -1,
                flavorText = flavorText ?: "",
                health = health ?: -1,
                id = id ?: -1,
                image = image ?: "",
                imageGold = imageGold ?: "",
                keywordIds = keywordIds ?: listOf(),
                manaCost = manaCost ?: -1,
                minionTypeId = minionTypeId ?: -1,
                multiClassIds = multiClassIds ?: listOf(),
                name = name ?: "",
                rarityId = rarityId ?: -1,
                slug = slug,
                spellSchoolId = spellSchoolId ?: -1,
                text = text ?: ""
            )
        }

}