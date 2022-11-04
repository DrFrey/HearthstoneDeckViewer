package com.freyapps.hearthstonedeckviewer.data.storage

import androidx.room.TypeConverter
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import com.freyapps.hearthstonedeckviewer.data.models.remote.ClassX
import com.freyapps.hearthstonedeckviewer.data.models.remote.Hero
import com.freyapps.hearthstonedeckviewer.data.models.remote.HeroPower
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

class HearthstoneClassConverter {
    @TypeConverter
    fun fromHearthstoneClass(hearthstoneClass: HearthstoneClass) = hearthstoneClass.value

    @TypeConverter
    fun toHearthstoneClass(string: String): HearthstoneClass {
        return try {
            HearthstoneClass.valueOf(string)
        } catch (e: Exception) {
            HearthstoneClass.UNKNOWN
        }
    }
}

class ClassConverter {
    @TypeConverter
    fun fromClassX(classX: ClassX): String = Gson().toJson(classX)

    @TypeConverter
    fun toClassX(json: String): ClassX? {
        return try {
            Gson().fromJson<ClassX>(json)
        } catch (e: Exception) {
            null
        }
    }
}

class CardConverter {
    @TypeConverter
    fun fromCards(cards: List<Card>): String = Gson().toJson(cards)

    @TypeConverter
    fun toCards(json: String): List<Card> {
        return try {
            Gson().fromJson<List<Card>>(json)
        } catch (e: Exception) {
            listOf()
        }
    }
}

class HeroPowerConverter {
    @TypeConverter
    fun fromHeroPower(heroPower: HeroPower): String = Gson().toJson(heroPower)

    @TypeConverter
    fun toHeroPower(json: String): HeroPower? {
        return try {
            Gson().fromJson<HeroPower>(json)
        } catch (e: Exception) {
            null
        }
    }
}

class HeroConverter {
    @TypeConverter
    fun fromHero(hero: Hero): String = Gson().toJson(hero)

    @TypeConverter
    fun toHero(json: String): Hero? {
        return try {
            Gson().fromJson<Hero>(json)
        } catch (e: Exception) {
            null
        }
    }
}