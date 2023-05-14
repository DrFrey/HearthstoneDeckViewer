package com.freyapps.hearthstonedeckviewer.domain.repositories

import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import com.freyapps.hearthstonedeckviewer.data.repository.Result
import kotlinx.coroutines.flow.Flow

interface BlizzardDeckRepository {

    suspend fun refreshBlizzardAccessToken(): Flow<Result<AccessDataRemote>>
    suspend fun getBlizzardDeckByCode(token: String, code: String): Flow<Result<DeckLocal>>
    suspend fun getBlizzardCardBySlug(slug: String, token: String): Flow<Result<CardLocal>>
}