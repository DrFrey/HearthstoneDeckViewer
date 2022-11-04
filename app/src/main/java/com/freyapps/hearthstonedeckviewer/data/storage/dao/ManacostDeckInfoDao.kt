package com.freyapps.hearthstonedeckviewer.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ManacostDeckInfoDao {

    @Query("SELECT * FROM ManacostDeckInfo WHERE hearthstoneClass = :hearthstoneClass ORDER BY date DESC")
    fun getDecksByClass(hearthstoneClass: HearthstoneClass): Flow<List<ManacostDeckInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deckInfo: ManacostDeckInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecks(decks: List<ManacostDeckInfo>)
}