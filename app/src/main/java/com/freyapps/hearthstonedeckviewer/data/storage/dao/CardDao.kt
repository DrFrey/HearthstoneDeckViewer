package com.freyapps.hearthstonedeckviewer.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("SELECT * FROM CardLocal")
    fun getAllCards() : Flow<List<CardLocal>>

    @Query("SELECT * FROM CardLocal WHERE slug = :slug")
    suspend fun getCardBySlug(slug: String) : CardLocal?

    @Query("DELETE FROM CardLocal")
    suspend fun deleteAll()

    @Insert
    suspend fun insertCard(card: CardLocal)

    @Delete
    suspend fun deleteCard(card: CardLocal)
}