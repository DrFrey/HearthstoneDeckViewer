package com.freyapps.hearthstonedeckviewer.data.repository

import android.util.Log
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import com.freyapps.hearthstonedeckviewer.data.remote.DataSourceRemote
import com.freyapps.hearthstonedeckviewer.data.storage.DataSourceLocal
import com.freyapps.hearthstonedeckviewer.domain.repositories.ManacostDecksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ManacostDecksRepositoryImpl(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceLocal: DataSourceLocal
) : ManacostDecksRepository {

    override fun localTopDecksByClass(hearthstoneClass: HearthstoneClass) =
        dataSourceLocal.getTopDecksByClass(hearthstoneClass)

    override suspend fun refreshTopDecksByClass(
        hearthstoneClass: HearthstoneClass
    ): Flow<Result<List<ManacostDeckInfo>>?> {
        return flow {
            var result: Result<List<ManacostDeckInfo>>? = null
            for (page in 1..5) {
                result = dataSourceRemote.getTopDecksByClass(hearthstoneClass, page)
                if (result is Result.Success) {
                    result.data?.let {
                        dataSourceLocal.insertTopDecks(it)
                        Log.d(TAG, "Imported ${it.size} ${hearthstoneClass.name} decks")
                    }
                } else {
                    break
                }
            }
            emit(result)
        }
    }

    companion object {
        private const val TAG = "DecksRepositoryImpl"
    }
}