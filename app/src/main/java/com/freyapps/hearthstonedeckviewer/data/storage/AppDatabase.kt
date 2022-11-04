package com.freyapps.hearthstonedeckviewer.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.storage.dao.DeckDao
import com.freyapps.hearthstonedeckviewer.data.storage.dao.ManacostDeckInfoDao

@Database(entities = [ManacostDeckInfo::class, DeckLocal::class], version = 1)
@TypeConverters(
    HearthstoneClassConverter::class,
    ClassConverter::class,
    CardConverter::class,
    HeroPowerConverter::class,
    HeroConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun manacostDeckInfoDao(): ManacostDeckInfoDao
    abstract fun deckDao(): DeckDao
}