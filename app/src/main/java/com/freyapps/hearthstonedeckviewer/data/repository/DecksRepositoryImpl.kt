package com.freyapps.hearthstonedeckviewer.data.repository

import android.util.Log
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import com.freyapps.hearthstonedeckviewer.data.remote.DataSourceRemote
import com.freyapps.hearthstonedeckviewer.data.storage.DataSourceLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class DecksRepositoryImpl(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceLocal: DataSourceLocal
) : DecksRepository {

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
            emit(Result.Success(dataSourceLocal.getDeckById(code)))
            emit(Result.Loading)
            val result = dataSourceRemote.getBlizzardDeckByCode(token, code)
            if (result is Result.Success) {
                result.data?.let {
                    dataSourceLocal.insertBlizzardDeck(it)
                }
                emit(result)
            } else {
                emit(Result.Error(Exception(result.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getBlizzardCardBySlug(
        slug: String,
        token: String
    ): Flow<Result<CardLocal>> {
        return flow {
            emit(Result.Success(dataSourceLocal.getCardBySlug(slug)))
            emit(Result.Loading)
            val result = dataSourceRemote.getBlizzardCardBySlug(slug, token)
            if (result is Result.Success) {
                result.data?.let {
                    dataSourceLocal.insertBlizzardCard(it)
                }
                emit(result)
            } else {
                emit(Result.Error(Exception(result.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }


    companion object {
        private const val TAG = "DecksRepositoryImpl"
    }
}