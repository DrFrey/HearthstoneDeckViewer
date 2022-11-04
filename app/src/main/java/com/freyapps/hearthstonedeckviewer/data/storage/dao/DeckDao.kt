package com.freyapps.hearthstonedeckviewer.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.repository.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Query("SELECT * FROM DeckLocal")
    fun getAllDecks() : Flow<List<DeckLocal>>

    @Query("SELECT * FROM DeckLocal WHERE code = :code")
    suspend fun getDeckById(code: String) : DeckLocal?

    @Query("DELETE FROM DeckLocal")
    suspend fun deleteAll()

    @Insert
    suspend fun insertDeck(deck: DeckLocal)

    @Delete
    suspend fun deleteDeck(deck: DeckLocal)
}