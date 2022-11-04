package com.freyapps.hearthstonedeckviewer.data.repository

import android.util.Log
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import com.freyapps.hearthstonedeckviewer.data.remote.DataSourceRemote
import com.freyapps.hearthstonedeckviewer.data.storage.DataSourceLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DecksRepositoryImpl(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceLocal: DataSourceLocal
) : DecksRepository {

    override suspend fun retrieveAllTopDecks() {
        refreshTopDecksByClass(HearthstoneClass.HUNTER)
        refreshTopDecksByClass(HearthstoneClass.WARRIOR)
        refreshTopDecksByClass(HearthstoneClass.DRUID)
        refreshTopDecksByClass(HearthstoneClass.MAGE)
        refreshTopDecksByClass(HearthstoneClass.SHAMAN)
        refreshTopDecksByClass(HearthstoneClass.PRIEST)
        refreshTopDecksByClass(HearthstoneClass.PALADIN)
        refreshTopDecksByClass(HearthstoneClass.DEMONHUNTER)
        refreshTopDecksByClass(HearthstoneClass.ROGUE)
        refreshTopDecksByClass(HearthstoneClass.WARLOCK)
    }

    override fun localTopDecksByClass(hearthstoneClass: HearthstoneClass) =
        dataSourceLocal.getTopDecksByClass(hearthstoneClass)

    override suspend fun refreshTopDecksByClass(hearthstoneClass: HearthstoneClass) {
        for (page in 1..5) {
            val result = dataSourceRemote.getTopDecksByClass(hearthstoneClass, page)
            if (result is Result.Success) {
                result.data?.let {
                    dataSourceLocal.insertTopDecks(it)
                    Log.d(TAG, "Imported ${it.size} ${hearthstoneClass.name} decks")
                }
            } else {
                break
            }
        }
    }

    override suspend fun refreshBlizzardAccessToken(): Flow<Result<AccessDataRemote>> {
        return flow {
            emit(dataSourceRemote.getBlizzardAuthToken())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getBlizzardDeckByCode(
        token: String,
        code: String
    ): Flow<Result<DeckLocal>> {
        return flow {
            val localResult = dataSourceLocal.getDeckById(code)
            if (localResult != null) {
                emit(Result.Success(localResult))
            } else {
                emit(Result.Loading)
                val result = dataSourceRemote.getBlizzardDeckByCode(token, code)
                if (result is Result.Success) {
                    result.data?.let {
                        dataSourceLocal.insertBlizzardDeck(it)
                    }
                }
                emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }


    companion object {
        private const val TAG = "DecksRepositoryImpl"
    }
}