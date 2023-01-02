package com.freyapps.hearthstonedeckviewer.domain.repositories

import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.repository.Result
import kotlinx.coroutines.flow.Flow

interface ManacostDecksRepository {

    suspend fun refreshTopDecksByClass(hearthstoneClass: HearthstoneClass): Flow<Result<List<ManacostDeckInfo>>?>
    fun localTopDecksByClass(hearthstoneClass: HearthstoneClass): Flow<List<ManacostDeckInfo>>
}