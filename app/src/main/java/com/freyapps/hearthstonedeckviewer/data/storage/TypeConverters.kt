package com.freyapps.hearthstonedeckviewer.data.storage

import androidx.room.TypeConverter
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.remote.*
import com.google.gson.Gson
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
    fun fromClassX(classX: ClassX?): String = Gson().toJson(classX)

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
    fun fromCards(cards: Map<Card, Int>): String = Gson().toJson(cards)

    @TypeConverter
    fun toCards(json: String): Map<Card, Int> {
        return try {
            Gson().fromJson<Map<Card, Int>>(json)
        } catch (e: Exception) {
            mapOf()
        }
    }
}

class HeroPowerConverter {
    @TypeConverter
    fun fromHeroPower(heroPower: HeroPower?): String = Gson().toJson(heroPower)

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
    fun fromHero(hero: Hero?): String = Gson().toJson(hero)

    @TypeConverter
    fun toHero(json: String): Hero? {
        return try {
            Gson().fromJson<Hero>(json)
        } catch (e: Exception) {
            null
        }
    }
}

class ListOfIntsConverter {
    @TypeConverter
    fun fromListOfInts(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun toListOfInts(json: String): List<Int>? {
        return try {
            Gson().fromJson<List<Int>>(json)
        } catch (e: Exception) {
            null
        }
    }
}

class DuelsConverter {
    @TypeConverter
    fun fromDuels(duels: Duels?): String = Gson().toJson(duels)

    @TypeConverter
    fun toDuels(json: String): Duels? {
        return try {
            Gson().fromJson<Duels>(json)
        } catch (e: Exception) {
            null
        }
    }
}