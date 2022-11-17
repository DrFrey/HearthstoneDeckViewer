package com.freyapps.hearthstonedeckviewer.data.remote

import com.freyapps.hearthstonedeckviewer.common.request
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.models.mappers.BlizzardCardMapper
import com.freyapps.hearthstonedeckviewer.data.models.mappers.BlizzardDeckMapper
import com.freyapps.hearthstonedeckviewer.data.models.mappers.TopDecksParser
import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import javax.inject.Inject
import com.freyapps.hearthstonedeckviewer.data.repository.Result
import com.freyapps.hearthstonedeckviewer.data.repository.map

class DataSourceRemote @Inject constructor(
    private val manacostApiService: ManacostApiService,
    private val blizzardAuthService: BlizzardAuthService,
    private val blizzardApiService: BlizzardApiService,
    private val topDecksParser: TopDecksParser,
    private val blizzardDeckMapper: BlizzardDeckMapper,
    private val blizzardCardMapper: BlizzardCardMapper
) {
    suspend fun getTopDecksByClass(
        hearthstoneClass: HearthstoneClass,
        page: Int
    ): Result<List<ManacostDeckInfo>> =
        request {
            manacostApiService.getTopDecksByClass(hearthstoneClass.value, page)
        }.map {
            topDecksParser.parseManacostDecks(it, hearthstoneClass)
        }

    suspend fun getBlizzardAuthToken(): Result<AccessDataRemote> =
        request {
            blizzardAuthService.getToken()
        }

    suspend fun getBlizzardDeckByCode(token: String, code: String): Result<DeckLocal> =
        request {
            blizzardApiService.getDeck(accessToken = token, code = code)
        }.map {
            blizzardDeckMapper.mapBlizzardDeckToLocal(it)
        }

    suspend fun getBlizzardCardBySlug(slug: String, token: String): Result<CardLocal> =
        request {
            blizzardApiService.getCard(slug = slug, accessToken = token)
        }.map {
            blizzardCardMapper.mapBlizzardCardToLocal(it)
        }
}