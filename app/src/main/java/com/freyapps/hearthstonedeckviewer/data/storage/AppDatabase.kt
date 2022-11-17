package com.freyapps.hearthstonedeckviewer.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.data.storage.dao.CardDao
import com.freyapps.hearthstonedeckviewer.data.storage.dao.DeckDao
import com.freyapps.hearthstonedeckviewer.data.storage.dao.ManacostDeckInfoDao

@Database(entities = [ManacostDeckInfo::class, DeckLocal::class, CardLocal::class], version = 2)
@TypeConverters(
    HearthstoneClassConverter::class,
    ClassConverter::class,
    CardConverter::class,
    HeroPowerConverter::class,
    HeroConverter::class,
    ListOfIntsConverter::class,
    DuelsConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun manacostDeckInfoDao(): ManacostDeckInfoDao
    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao
}