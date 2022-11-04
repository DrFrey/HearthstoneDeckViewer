package com.freyapps.hearthstonedeckviewer.data.repository

import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import kotlinx.coroutines.flow.Flow

interface DecksRepository {

    suspend fun retrieveAllTopDecks()
    suspend fun refreshTopDecksByClass(hearthstoneClass: HearthstoneClass)
    fun localTopDecksByClass(hearthstoneClass: HearthstoneClass): Flow<List<ManacostDeckInfo>>

    suspend fun refreshBlizzardAccessToken(): Flow<Result<AccessDataRemote>>
    suspend fun getBlizzardDeckByCode(token: String, code: String): Flow<Result<DeckLocal>>
}